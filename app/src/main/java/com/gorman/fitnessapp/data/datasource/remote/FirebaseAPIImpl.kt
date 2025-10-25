package com.gorman.fitnessapp.data.datasource.remote

import android.util.Log
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
import com.gorman.fitnessapp.domain.models.UsersData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.forEach

class FirebaseAPIImpl @Inject constructor(
    private val database: DatabaseReference
) : FirebaseAPI {
    override suspend fun getExercises(): List<ExerciseFirebase> {
        val exerciseRef = database.child("exercises")
        val dataSnapshot = exerciseRef.get().await()
        val exerciseList = mutableListOf<ExerciseFirebase>()
        for (exerciseSnapshot in dataSnapshot.children) {
            val exercise = exerciseSnapshot.getValue<ExerciseFirebase>()
            if (exercise != null) {
                exerciseList.add(exercise.copy(id = exerciseSnapshot.key ?: ""))
            }
        }
        return exerciseList
    }

    override suspend fun getUser(email: String): UserFirebase? {
        val usersRef = database.child("users")
        val query = usersRef.orderByChild("email").equalTo(email)
        val dataSnapshot = query.get().await()
        if (!dataSnapshot.exists()) {
            return null
        }
        val userSnapshot = dataSnapshot.children.first()
        return userSnapshot.getValue<UserFirebase>()
    }

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
        Log.d("FirebaseAPI", "Успешно удалены старые программы и связанные данные.")
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
            Log.e("FirebaseAPI", "Program ID is null. Cannot insert exercises.")
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
        Log.d("ProgramExercise", "$exercisesMap")
    }

    override suspend fun insertUserProgram(program: UserProgramFirebase) {
        val userProgramRef = database.child("user_program").push()
        userProgramRef.setValue(program).await()
    }

    override suspend fun insertUser(user: UsersData) {
        val usersRef = database.child("users")
        val userIdRef = usersRef.push()
        val userId = userIdRef.key
        userId?.let { userIdRef.setValue(user.toRemote(it)).await() }
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
        mealPlanTemplateFirebase: MealPlanTemplateFirebase
    ): String? {
        val mealPlanTemplateRef = database.child("meal_plan_templates")
        val newMealPlanTemplate = mealPlanTemplateRef.push()
        val newMealPlanTemplateId = newMealPlanTemplate.key
            ?: throw IllegalStateException("Failed to generate key for meal plan template.")
        val mealPlanItemsRef = database.child("meal_plan_items")
            .child(newMealPlanTemplateId)
        newMealPlanTemplate.setValue(mealPlanTemplateFirebase).await()
        if (mealPlanItemFirebase.isEmpty()) {
            return null
        }
        val itemsUpdateMap = mutableMapOf<String, Any>()
        mealPlanItemFirebase.forEach { item ->
            val newItemId = mealPlanItemsRef.push().key ?: return@forEach
            itemsUpdateMap[newItemId] = item
        }
        mealPlanItemsRef.updateChildren(itemsUpdateMap).await()
        return newMealPlanTemplateId
    }

    override suspend fun getMealPlans(userId: Int): Map<List<MealPlanItemFirebase>, MealPlanTemplateFirebase> {
        TODO("Not yet implemented")
    }
}