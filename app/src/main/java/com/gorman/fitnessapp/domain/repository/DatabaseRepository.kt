package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.data.models.AddUserResponse
import com.gorman.fitnessapp.data.models.UsersDataEntity
import retrofit2.Response

interface DatabaseRepository {
    suspend fun getAllUsers(): List<UsersDataEntity>
    suspend fun addUser(user: UsersDataEntity)
    //suspend fun addUser(user: UsersDataEntity): Response<AddUserResponse>
}