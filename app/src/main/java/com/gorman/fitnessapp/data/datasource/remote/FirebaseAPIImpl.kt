package com.gorman.fitnessapp.data.datasource.remote

import android.util.Log
import androidx.compose.foundation.gestures.forEach
import androidx.compose.ui.input.key.key
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.data.models.firebase.ExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.room.ExerciseEntity
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

    override suspend fun insertProgram(program: ProgramFirebase): String? {
        val newProgramRef = database.child("program").push()
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
    }

    override suspend fun insertUser(user: UsersData) {
        val usersRef = database.child("users")
        val userIdRef = usersRef.push()
        val userId = userIdRef.key
        userId?.let { userIdRef.setValue(user.toRemote(it)).await() }
    }
}