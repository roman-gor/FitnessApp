package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val usersDataDao: UsersDataDao
): DatabaseRepository {

    override suspend fun getAllUsers(): List<UsersData> {
        return usersDataDao.getAllUsers()
            .map { it.toDomain() }
    }

    override suspend fun getUser(email: String?): UsersData {
        return usersDataDao.getUserByEmail(email).toDomain()
    }

    override suspend fun addUser(user: UsersData) {
        return usersDataDao.addUser(user.toEntity())
    }

    override suspend fun deleteUser(user: UsersData) {
        return usersDataDao.deleteUser(user.toEntity())
    }

    override suspend fun updateUser(user: UsersData): Int {
        return usersDataDao.updateUser(user.toEntity())
    }
}