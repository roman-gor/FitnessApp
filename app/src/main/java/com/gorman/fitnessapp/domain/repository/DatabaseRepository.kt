package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.UsersData

interface DatabaseRepository {
    suspend fun getAllUsers(): List<UsersData>
    suspend fun getUser(email: String?): UsersData
    suspend fun addUser(user: UsersData)
    suspend fun deleteUser(user: UsersData)
    suspend fun updateUser(user: UsersData): Int
    suspend fun getExercises(): List<Exercise>
    suspend fun insertExercises(exercises: List<Exercise>)
}