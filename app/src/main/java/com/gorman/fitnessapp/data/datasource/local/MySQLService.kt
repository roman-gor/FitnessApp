package com.gorman.fitnessapp.data.datasource.local

import com.gorman.fitnessapp.data.models.AddUserResponse
import com.gorman.fitnessapp.data.models.UsersDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MySQLService {
    @GET("api/get_users.php")
    //Метод для получения всех пользователей из базы данных
    suspend fun getUsers(): List<UsersDataEntity>

    @POST("api/add_user.php")
    //Метод для добавления пользователя в базу данных
    suspend fun addUser(@Body user: UsersDataEntity): Response<AddUserResponse>
}