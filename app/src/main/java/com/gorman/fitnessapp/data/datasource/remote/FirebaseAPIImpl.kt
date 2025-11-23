package com.gorman.fitnessapp.data.datasource.remote

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.data.models.firebase.ArticleFirebase
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
            throw e
        }
    }

    override suspend fun getExercises(): List<ExerciseFirebase> = executeRequest("Получение упражнений") {
        val snapshot = database.child("exercises").get().await()
        snapshot.children.mapNotNull { snap ->
            snap.getValue<ExerciseFirebase>()?.copy(id = snap.key ?: "")
        }
    } ?: emptyList()

    override suspend fun findUserPrograms(userId: String): Map<String, UserProgramFirebase> =
        executeRequest("Поиск программ пользователя $userId") {
            val userProgramsRef = database.child("user_program")
            val query = userProgramsRef.orderByChild("userId").equalTo(userId)
            val snapshot = query.get().await()

            if (!snapshot.exists()) return@executeRequest emptyMap()

            snapshot.children.associate { child ->
                val key = child.key!!
                val value = child.getValue(UserProgramFirebase::class.java)!!
                key to value
            }
        } ?: emptyMap()

    override suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgramFirebase>) {
        if (userPrograms.isEmpty()) return

        executeRequest("Удаление старых программ пользователя") {
            val updates = mutableMapOf<String, Any?>()
            userPrograms.forEach { (userProgramKey, userProgramValue) ->
                updates["/user_program/$userProgramKey"] = null
                updates["/program/${userProgramValue.programId}"] = null
                updates["/program_exercise/${userProgramValue.programId}"] = null
            }
            database.updateChildren(updates).await()
        }
    }

    override suspend fun insertProgram(program: ProgramFirebase): String? = executeRequest("Вставка программы") {
        val programRef = database.child("program")
        val newProgramRef = programRef.push()
        val programId = newProgramRef.key ?: throw IllegalStateException("Firebase push key is null")
        newProgramRef.setValue(program).await()
        programId
    }

    override suspend fun insertProgramExercise(programExercise: List<ProgramExerciseFirebase>?, programId: String?) {
        if (programId.isNullOrBlank()) {
            logger.e("FirebaseAPI", "Program ID is null. Cannot insert exercises.")
            return
        }

        executeRequest("Вставка упражнений для программы $programId") {
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
    }

    override suspend fun insertUserProgram(program: UserProgramFirebase) {
        executeRequest("Вставка программы пользователя ${program.userId}") {
            val userProgramRef = database.child("user_program").push()
            userProgramRef.setValue(program).await()
        }
    }

    override suspend fun insertUserProgress(userProgress: UserProgressFirebase): String? =
        executeRequest("Вставка прогресса пользователя ${userProgress.userId} и обновление веса") {
            val userProgressRef = database.child("user_progress").child(userProgress.userId).push()
            Log.d("UserProgress", userProgress.userId)
            val newProgressKey = userProgressRef.key
                ?: throw IllegalStateException("Не удалось сгенерировать ключ для user_progress")

            val updates = mutableMapOf<String, Any?>()
            updates["/user_progress/${userProgress.userId}/$newProgressKey"] = userProgress
            updates["/users/${userProgress.userId}/weight"] = userProgress.weight
            database.updateChildren(updates).await()
            newProgressKey
        }

    override suspend fun getUserProgress(userId: String): List<UserProgressFirebase>? =
        executeRequest("Получение прогресса пользователя $userId") {
            val userProgressRef = database.child("user_progress")
            val query = userProgressRef.orderByChild("userId").equalTo(userId)
            val dataSnapshot = query.get().await()

            if (!dataSnapshot.exists()) return@executeRequest null

            dataSnapshot.children.mapNotNull { progressSnapshot ->
                progressSnapshot.getValue<UserProgressFirebase>()?.copy(id = progressSnapshot.key ?: "")
            }
        }

    override suspend fun getUser(email: String): UserFirebase? = executeRequest("Получение пользователя по email: $email") {
        val usersRef = database.child("users")
        val query = usersRef.orderByChild("email").equalTo(email)
        val dataSnapshot = query.get().await()
        if (!dataSnapshot.exists()) {
            return@executeRequest null
        }
        val userSnapshot = dataSnapshot.children.first()
        val userId = userSnapshot.key
        val user = userSnapshot.getValue(UserFirebase::class.java)
        if (user != null && userId != null) {
            user.copy(userId = userId)
        } else {
            null
        }
    }

    override suspend fun insertUser(user: UsersData): String? = executeRequest("Вставка нового пользователя") {
        val usersRef = database.child("users")
        val existingUserSnapshot = usersRef
            .orderByChild("email")
            .equalTo(user.email)
            .get()
            .await()
        if (existingUserSnapshot.exists()) {
            throw IllegalStateException("Пользователь с таким email уже существует")
        }
        val userIdRef = usersRef.push()
        val userId = userIdRef.key
            ?: throw IllegalStateException("Не удалось сгенерировать ключ для пользователя")
        userIdRef.setValue(user.toRemote(userId)).await()
        userId
    }

    override suspend fun deleteUser(user: UserFirebase) {
        if (user.userId.isBlank()) {
            logger.e("FirebaseAPI", "Не указан userId для удаления пользователя.")
            return
        }
        executeRequest("Удаление пользователя с ID: ${user.userId}") {
            val updates = mutableMapOf<String, Any?>()
            updates["/user_program/${user.userId}"] = null
            updates["/users/${user.userId}"] = null
            updates["/user_progress/${user.userId}"] = null
            updates["/workout_history/${user.userId}"] = null
            database.updateChildren(updates)
        }
    }

    override suspend fun updateUser(user: UserFirebase) {
        if (user.userId.isBlank()) {
            logger.e("FirebaseAPI", "Не указан userId для обновления пользователя.")
            return
        }
        executeRequest("Обновление пользователя с ID: ${user.userId}") {
            database.child("users").child(user.userId).setValue(user).await()
        }
    }

    override suspend fun getMeals(): List<MealFirebase> = executeRequest("Получение списка приемов пищи") {
        val mealsRef = database.child("meals")
        val mealsSnapshot = mealsRef.get().await()
        mealsSnapshot.children.mapNotNull { mealSnap ->
            mealSnap.getValue<MealFirebase>()?.copy(id = mealSnap.key ?: "")
        }
    } ?: emptyList()

    override suspend fun insertMealPlan(
        mealPlanItemFirebase: List<MealPlanItemFirebase>,
        mealPlanTemplateFirebase: MealPlanTemplateFirebase,
        userId: String
    ): String? = executeRequest("Вставка плана питания для пользователя $userId") {
        val mealPlanTemplateRef = database.child("meal_plan_templates")
        val newMealPlanTemplate = mealPlanTemplateRef.push()
        val newMealPlanTemplateId = newMealPlanTemplate.key
            ?: throw IllegalStateException("Failed to generate key for meal plan template.")

        newMealPlanTemplate.setValue(mealPlanTemplateFirebase.copy(userId = userId)).await()

        if (mealPlanItemFirebase.isNotEmpty()) {
            val mealPlanItemsRef = database.child("meal_plan_items").child(newMealPlanTemplateId)
            val itemsUpdateMap = mutableMapOf<String, Any>()
            mealPlanItemFirebase.forEach { item ->
                val newItemId = mealPlanItemsRef.push().key ?: return@forEach
                itemsUpdateMap[newItemId] = item.copy(templateId = newMealPlanTemplateId)
            }
            mealPlanItemsRef.updateChildren(itemsUpdateMap).await()
        }
        newMealPlanTemplateId
    }

    override suspend fun findUserMealPlanTemplate(userId: String): Map<String, MealPlanTemplateFirebase> =
        executeRequest("Поиск шаблонов плана питания для пользователя $userId") {
            val templatesRef = database.child("meal_plan_templates")
            val query = templatesRef.orderByChild("userId").equalTo(userId)
            val snapshot = query.get().await()

            if (!snapshot.exists()) return@executeRequest emptyMap()

            snapshot.children.associate { child ->
                val key = child.key!!
                val value = child.getValue(MealPlanTemplateFirebase::class.java)!!
                key to value
            }
        } ?: emptyMap()

    override suspend fun deleteMealPlan(templateId: String) {
        executeRequest("Удаление плана питания и его элементов с ID: $templateId") {
            val updates = mutableMapOf<String, Any?>()
            updates["/meal_plan_templates/$templateId"] = null
            updates["/meal_plan_items/$templateId"] = null
            database.updateChildren(updates).await()
        }
    }

    override suspend fun getProgram(programId: String): ProgramFirebase? =
        executeRequest("Получение программы с ID: $programId") {
            val programRef = database.child("program").child(programId)
            val snapshot = programRef.get().await()
            if (!snapshot.exists()) return@executeRequest null
            snapshot.getValue<ProgramFirebase>()
        }

    override suspend fun getProgramExercises(programId: String): List<ProgramExerciseFirebase> =
        executeRequest("Получение упражнений для программы с ID: $programId") {
            val programExerciseRef = database.child("program_exercise").child(programId)
            val snapshot = programExerciseRef.get().await()
            if (!snapshot.exists()) return@executeRequest emptyList()
            snapshot.children.mapNotNull { child ->
                child.getValue(ProgramExerciseFirebase::class.java)
            }
        } ?: emptyList()

    override suspend fun getUserProgram(userId: String): UserProgramFirebase? =
        executeRequest("Получение активной программы для пользователя $userId") {
            val userProgramRef = database.child("user_program")
            val query = userProgramRef.orderByChild("userId").equalTo(userId)
            val snapshot = query.get().await()
            if (!snapshot.exists()) return@executeRequest null
            snapshot.children.first().getValue<UserProgramFirebase>()
        }

    override suspend fun getMealPlans(userId: String): Map<String, Pair<MealPlanTemplateFirebase, List<MealPlanItemFirebase>>>? =
        executeRequest("Получение планов питания пользователя $userId") {
            val templatesRef = database.child("meal_plan_templates")
            val templateQuery = templatesRef.orderByChild("userId").equalTo(userId)
            val templateSnapshot = templateQuery.get().await()

            if (!templateSnapshot.exists()) {
                logger.d("FirebaseAPI", "Шаблоны планов питания для пользователя $userId не найдены.")
                return@executeRequest null
            }

            val userTemplateSnapshot = templateSnapshot.children.first()
            val templateId = userTemplateSnapshot.key
            val template = userTemplateSnapshot.getValue(MealPlanTemplateFirebase::class.java)

            if (templateId == null || template == null) {
                logger.e("FirebaseAPI", "Не удалось получить ID или данные шаблона.")
                return@executeRequest null
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
            mapOf(templateId to Pair(template, mealItems))
        }

    override suspend fun insertWorkoutHistory(workoutHistoryFirebase: WorkoutHistoryFirebase): String? =
        executeRequest("Вставка истории тренировки") {
            val workoutHistoryRef = database.child("workout_history").child(workoutHistoryFirebase.userId).push()
            val newHistoryKey = workoutHistoryRef.key
                ?: throw IllegalStateException("Не удалось сгенерировать ключ для workout_history")
            workoutHistoryRef.setValue(workoutHistoryFirebase).await()
            newHistoryKey
        }

    override suspend fun getWorkoutHistory(userId: String): List<WorkoutHistoryFirebase> =
        executeRequest("Получение истории тренировок для пользователя $userId") {
            val workoutHistoryRef = database.child("workout_history").child(userId)
            val dataSnapshot = workoutHistoryRef.get().await()
            if (!dataSnapshot.exists()) {
                return@executeRequest emptyList()
            }
            dataSnapshot.children.mapNotNull { historySnapshot ->
                historySnapshot.getValue<WorkoutHistoryFirebase>()?.copy(id = historySnapshot.key ?: "")
            }
        } ?: emptyList()

    override suspend fun getArticles(): List<ArticleFirebase> = executeRequest("Получение статей") {
        val articlesRef = database.child("article")
        val articlesSnapshot = articlesRef.get().await()
        articlesSnapshot.children.mapNotNull { articleSnap ->
            articleSnap.getValue<ArticleFirebase>()
        }
    } ?: throw IllegalStateException("Не удалось загрузить статьи")
}