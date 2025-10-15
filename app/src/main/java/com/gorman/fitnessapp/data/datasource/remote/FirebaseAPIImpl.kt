package com.gorman.fitnessapp.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.room.ExerciseEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAPIImpl @Inject constructor(
    private val database: DatabaseReference
) : FirebaseAPI {
    override suspend fun getExercises(): List<ExerciseEntity> {
        val exerciseRef = database.child("exercises")
        val dataSnapshot = exerciseRef.get().await()
        val exerciseList = mutableListOf<ExerciseEntity>()
        for (exerciseSnapshot in dataSnapshot.children) {
            val exercise = exerciseSnapshot.getValue<ExerciseEntity>()
            if (exercise != null) {
                exerciseList.add(exercise)
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

    override suspend fun insertProgram(program: ProgramFirebase) {
        val programRef = database.child("program")
        programRef.child(program.id.toString()).setValue(program).await()
    }

    override suspend fun insertProgramExercise(programExercise: ProgramExerciseFirebase) {
        val programExerciseRef = database.child("program_exercise")
        programExerciseRef.child(programExercise.programId.toString()).setValue(programExercise).await()
    }

    override suspend fun insertUser(user: UserFirebase) {
        val usersRef = database.child("users")
        usersRef.child(user.id.toString()).setValue(user).await()
    }
}