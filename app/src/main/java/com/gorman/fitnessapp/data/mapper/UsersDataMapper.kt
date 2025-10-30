package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.room.UsersDataEntity
import com.gorman.fitnessapp.domain.models.UsersData

fun UsersDataEntity.toDomain(): UsersData =
    UsersData(
        id = id,
        name = name,
        email = email,
        birthday = birthday,
        goal = goal,
        weight = weight,
        desiredWeight = desiredWeight,
        height = height,
        gender = gender,
        photoUrl = photoUrl,
        activityLevel = activityLevel,
        experienceLevel = experienceLevel)

fun UsersData.toEntity(userId: Int = 0): UsersDataEntity =
    UsersDataEntity(
        id = userId,
        firebaseId = firebaseId,
        name = name,
        email = email,
        birthday = birthday,
        goal = goal,
        weight = weight,
        desiredWeight = desiredWeight,
        height = height,
        gender = gender,
        photoUrl = photoUrl,
        activityLevel = activityLevel,
        experienceLevel = experienceLevel)

fun UsersData.toRemote(userId: String): UserFirebase =
    UserFirebase(
        userId = userId,
        name = name,
        email = email,
        birthday = birthday,
        goal = goal,
        weight = weight,
        desiredWeight = desiredWeight,
        height = height,
        gender = gender,
        photoUrl = photoUrl,
        activityLevel = activityLevel,
        experienceLevel = experienceLevel)

fun UserFirebase.toDomain(): UsersData =
    UsersData(
        firebaseId = userId,
        name = name,
        email = email,
        birthday = birthday,
        goal = goal,
        weight = weight,
        desiredWeight = desiredWeight,
        height = height,
        gender = gender,
        photoUrl = photoUrl,
        activityLevel = activityLevel,
        experienceLevel = experienceLevel
    )