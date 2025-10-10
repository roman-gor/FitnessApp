package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.models.UsersDataEntity
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    //private val api: MySQLService
    private val usersDataDao: UsersDataDao
): DatabaseRepository {

    override suspend fun getAllUsers(): List<UsersDataEntity> {
        return usersDataDao.getAllUsers()
    }

    override suspend fun addUser(user: UsersDataEntity) {
        return usersDataDao.addUser(user)
    }
}