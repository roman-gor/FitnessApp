package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.UsersDataEntity
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

fun UsersData.toEntity(): UsersDataEntity =
    UsersDataEntity(
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