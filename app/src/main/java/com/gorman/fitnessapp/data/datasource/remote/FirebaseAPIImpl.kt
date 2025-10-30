package com.gorman.fitnessapp.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.data.models.firebase.ExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.MealFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanItemFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanTemplateFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.firebase.UserProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserProgressFirebase
import com.gorman.fitnessapp.data.models.firebase.WorkoutHistoryFirebase
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.logger.AppLogger
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach

class FirebaseAPIImpl @Inject constructor(
    private val database: DatabaseReference,
    private val logger: AppLogger
) : FirebaseAPI {
    private suspend fun <T> executeRequest(
        operationName: String,
        block: suspend () -> T
    ): T? {
        return try {
            val result = block()
            logger.d("FirebaseAPI", "$operationName успешно выполнено")
            result
        } catch (e: Exception) {
            logger.e("FirebaseAPI", "Ошибка при выполнении $operationName: ${e.message}")
            null
        }
    }

    override suspend fun getExercises(): List<ExerciseFirebase> = executeRequest("Получение упражнений") {
        val snapshot = database.child("exercises").get().await()
        snapshot.children.mapNotNull { snap ->
            snap.getValue<ExerciseFirebase>()?.copy(id = snap.key ?: "")
        }
    } ?: emptyList()

    override suspend fun findUserPrograms(userId: String): Map<String, UserProgramFirebase> {
        val userProgramsRef = database.child("user_program")
        val query = userProgramsRef.orderByChild("userId").equalTo(userId)
        val snapshot = query.get().await()

        if (!snapshot.exists()) return emptyMap()

        return snapshot.children.associate { child ->
            val key = child.key!!
            val value = child.getValue(UserProgramFirebase::class.java)!!
            key to value
        }
    }

    override suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgramFirebase>) {
        if (userPrograms.isEmpty()) return

        val updates = mutableMapOf<String, Any?>()
        userPrograms.forEach { (userProgramKey, userProgramValue) ->
            updates["/user_program/$userProgramKey"] = null
            updates["/program/${userProgramValue.programId}"] = null
            updates["/program_exercise/${userProgramValue.programId}"] = null
        }
        database.updateChildren(updates).await()
        logger.d("FirebaseAPI", "Успешно удалены старые программы и связанные данные.")
    }

    override suspend fun insertProgram(program: ProgramFirebase): String? {
        val programRef = database.child("program")
        val newProgramRef = programRef.push()
        val programId = newProgramRef.key ?: throw IllegalStateException("Firebase push key is null")
        newProgramRef.setValue(program).await()
        return programId
    }

    override suspend fun insertProgramExercise(programExercise: List<ProgramExerciseFirebase>?, programId: String?) {
        if (programId.isNullOrBlank()) {
            logger.e("FirebaseAPI", "Program ID is null. Cannot insert exercises.")
            return
        }
        val programExerciseRef = database.child("program_exercise").child(programId)
        val exercisesMap = mutableMapOf<String, Any>()
        programExercise?.forEach { exercise ->
            val exerciseId = programExerciseRef.push().key ?: return@forEach
            exercisesMap[exerciseId] = exercise
        }
        if (exercisesMap.isNotEmpty()) {
            programExerciseRef.updateChildren(exercisesMap).await()
        }
        logger.d("ProgramExercise", "$exercisesMap")
    }

    override suspend fun insertUserProgram(program: UserProgramFirebase) {
        val userProgramRef = database.child("user_program").push()
        userProgramRef.setValue(program).await()
    }

    override suspend fun insertUserProgress(userProgress: UserProgressFirebase): String? {
        val userProgressRef = database.child("user_progress").push()
        val newProgressKey = userProgressRef.key
        if (newProgressKey == null) {
            logger.e("FirebaseAPI", "Не удалось сгенерировать ключ для user_progress")
            return null
        }
        val updates = mutableMapOf<String, Any?>()
        updates["/user_progress/$newProgressKey"] = userProgress
        updates["/users/${userProgress.userId}/weight"] = userProgress.weight
        database.updateChildren(updates).await()
        logger.d("FirebaseAPI", "Прогресс для пользователя ${userProgress.userId} добавлен, вес в профиле обновлен.")
        return newProgressKey
    }

    override suspend fun updateUserProgress(userProgress: UserProgressFirebase) {
        val userProgressRef = database.child("user_progress").child(userProgress.userId)
        userProgressRef.setValue(userProgress).await()
    }

    override suspend fun getUserProgress(userId: String): List<UserProgressFirebase>? {
        val userProgressRef = database.child("user_progress")
        val query = userProgressRef.orderByChild("userId").equalTo(userId)
        val dataSnapshot = query.get().await()
        if (!dataSnapshot.exists()) {
            return null
        }
        val progressList = mutableListOf<UserProgressFirebase>()
        for (progressSnapshot in dataSnapshot.children) {
            val userProgress = progressSnapshot.getValue<UserProgressFirebase>()
            if (userProgress != null) {
                progressList.add(userProgress.copy(id = progressSnapshot.key ?: ""))
            }
        }
        return progressList
    }

    override suspend fun getUser(email: String): UserFirebase? {
        val usersRef = database.child("users")
        val query = usersRef.orderByChild("email").equalTo(email)
        val dataSnapshot = query.get().await()
        if (!dataSnapshot.exists()) {
            return null
        }
        val userSnapshot = dataSnapshot.children.first()
        val userId = userSnapshot.key
        val user = userSnapshot.getValue(UserFirebase::class.java)
        return if (user != null && userId != null) {
            user.copy(userId = userId)
        } else {
            null
        }
    }

    override suspend fun insertUser(user: UsersData): String? {
        val usersRef = database.child("users")
        val userIdRef = usersRef.push()
        val userId = userIdRef.key
        userId?.let { userIdRef.setValue(user.toRemote(it)).await() }
        return userId
    }

    override suspend fun deleteUser(user: UserFirebase) {
        if (user.userId.isBlank()) {
            logger.e("FirebaseAPI", "Не указан userId для удаления пользователя.")
            return
        }
        try {
            database.child("users").child(user.userId).setValue(null).await()
            logger.d("FirebaseAPI", "Успешно удален пользователь с ID: ${user.userId}")
        } catch (e: Exception) {
            logger.e("FirebaseAPI", "Ошибка при удалении пользователя ${user.userId}: ${e.message}")
        }
    }

    override suspend fun updateUser(user: UserFirebase) {
        if (user.userId.isBlank()) {
            logger.e("FirebaseAPI", "Не указан userId для обновления пользователя.")
            return
        }
        try {
            database.child("users").child(user.userId).setValue(user).await()
            logger.d("FirebaseAPI", "Успешно обновлен пользователь с ID: ${user.userId}")
        } catch (e: Exception) {
            logger.e("FirebaseAPI", "Ошибка при обновлении пользователя ${user.userId}: ${e.message}")
        }
    }

    override suspend fun getMeals(): List<MealFirebase> {
        val mealsRef = database.child("meals")
        val mealsSnapshot = mealsRef.get().await()
        val mealsList = mutableListOf<MealFirebase>()
        for (mealSnap in mealsSnapshot.children) {
            val meal = mealSnap.getValue<MealFirebase>()
            meal?.let { mealsList.add(it.copy(id = mealSnap.key ?: "")) }
        }
        return mealsList
    }

    override suspend fun insertMealPlan(
        mealPlanItemFirebase: List<MealPlanItemFirebase>,
        mealPlanTemplateFirebase: MealPlanTemplateFirebase,
        userId: String
    ): String? {
        val mealPlanTemplateRef = database.child("meal_plan_templates")
        val newMealPlanTemplate = mealPlanTemplateRef.push()
        val newMealPlanTemplateId = newMealPlanTemplate.key
            ?: throw IllegalStateException("Failed to generate key for meal plan template.")
        val mealPlanItemsRef = database.child("meal_plan_items")
            .child(newMealPlanTemplateId)
        newMealPlanTemplate.setValue(mealPlanTemplateFirebase.copy(userId = userId)).await()
        if (mealPlanItemFirebase.isEmpty()) {
            return null
        }
        val itemsUpdateMap = mutableMapOf<String, Any>()
        mealPlanItemFirebase.forEach { item ->
            val newItemId = mealPlanItemsRef.push().key ?: return@forEach
            itemsUpdateMap[newItemId] = item.copy(templateId = newMealPlanTemplateId)
        }
        mealPlanItemsRef.updateChildren(itemsUpdateMap).await()
        return newMealPlanTemplateId
    }

    override suspend fun findUserMealPlanTemplate(userId: String): Map<String, MealPlanTemplateFirebase> {
        val templatesRef = database.child("meal_plan_templates")
        val query = templatesRef.orderByChild("userId").equalTo(userId)
        val snapshot = query.get().await()

        if (!snapshot.exists()) return emptyMap()

        return snapshot.children.associate { child ->
            val key = child.key!!
            val value = child.getValue(MealPlanTemplateFirebase::class.java)!!
            key to value
        }
    }

    override suspend fun deleteMealPlan(templateId: String) {
        val updates = mutableMapOf<String, Any?>()
        updates["/meal_plan_templates/$templateId"] = null
        updates["/meal_plan_items/$templateId"] = null
        database.updateChildren(updates).await()
        logger.d("FirebaseAPI", "Успешно удален старый план питания: $templateId")
    }

    override suspend fun getProgram(programId: String): ProgramFirebase? {
        val programRef = database.child("program").child(programId)
        val snapshot = programRef.get().await()
        if (!snapshot.exists()) return null
        return snapshot.getValue<ProgramFirebase>()
    }

    override suspend fun getProgramExercises(programId: String): List<ProgramExerciseFirebase> {
        val programExerciseRef = database.child("program_exercise").child(programId)
        val snapshot = programExerciseRef.get().await()
        if (!snapshot.exists()) return emptyList()
        return snapshot.children.mapNotNull { child->
            child.getValue(ProgramExerciseFirebase::class.java)
        }
    }

    override suspend fun getUserProgram(userId: String): UserProgramFirebase? {
        val userProgramRef = database.child("user_program")
        val query = userProgramRef.orderByChild("userId").equalTo(userId)
        val snapshot = query.get().await()
        if (!snapshot.exists()) return null
        val userProgram = snapshot.children.first()
        return userProgram.getValue<UserProgramFirebase>()
    }

    override suspend fun getMealPlans(userId: String): Map<String, Pair<MealPlanTemplateFirebase, List<MealPlanItemFirebase>>>? {
        val templatesRef = database.child("meal_plan_templates")
        val templateQuery = templatesRef.orderByChild("userId").equalTo(userId)
        val templateSnapshot = templateQuery.get().await()

        if (!templateSnapshot.exists()) {
            logger.d("FirebaseAPI", "Шаблоны планов питания для пользователя $userId не найдены.")
            return null
        }

        val userTemplateSnapshot = templateSnapshot.children.first()
        val templateId = userTemplateSnapshot.key
        val template = userTemplateSnapshot.getValue(MealPlanTemplateFirebase::class.java)

        if (templateId == null || template == null) {
            logger.e("FirebaseAPI", "Не удалось получить ID или данные шаблона.")
            return null
        }

        val itemsRef = database.child("meal_plan_items").child(templateId)
        val itemsSnapshot = itemsRef.get().await()

        val mealItems = if (itemsSnapshot.exists()) {
            itemsSnapshot.children.mapNotNull { child ->
                child.getValue(MealPlanItemFirebase::class.java)
            }
        } else {
            emptyList()
        }
        return mapOf(templateId to Pair(template, mealItems))
    }

    override suspend fun insertWorkoutHistory(workoutHistoryFirebase: WorkoutHistoryFirebase): String? {
        val workoutHistoryRef = database.child("workout_history").push()
        val newHistoryKey = workoutHistoryRef.key
        if (newHistoryKey == null) {
            logger.e("FirebaseAPI", "Не удалось сгенерировать ключ для workout_history")
            return null
        }
        workoutHistoryRef.setValue(workoutHistoryFirebase).await()
        logger.d("FirebaseAPI", "Успешно добавлена запись тренировки: $newHistoryKey")
        return newHistoryKey
    }

    override suspend fun updateWorkoutHistory(workoutHistoryFirebase: WorkoutHistoryFirebase, userId: String) {
        if (workoutHistoryFirebase.id.isBlank()) {
            logger.e("FirebaseAPI", "Не указан ID для обновления истории тренировки.")
            return
        }
        val workoutHistoryRef = database.child("workout_history").child(userId).child(workoutHistoryFirebase.id)
        workoutHistoryRef.setValue(workoutHistoryFirebase).await()
        logger.d("FirebaseAPI", "Запись истории тренировки ${workoutHistoryFirebase.id} успешно обновлена")
    }

    override suspend fun getWorkoutHistory(userId: String): List<WorkoutHistoryFirebase> {
        val workoutHistoryRef = database.child("workout_history").child(userId)
        val dataSnapshot = workoutHistoryRef.get().await()
        if (!dataSnapshot.exists()) {
            return emptyList()
        }
        val historyList = mutableListOf<WorkoutHistoryFirebase>()
        for (historySnapshot in dataSnapshot.children) {
            val workoutHistory = historySnapshot.getValue<WorkoutHistoryFirebase>()
            if (workoutHistory != null) {
                historyList.add(workoutHistory.copy(id = historySnapshot.key ?: ""))
            }
        }
        logger.d("FirebaseAPI", "Найдено ${historyList.size} записей истории тренировок для пользователя $userId")
        return historyList
    }

}