package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.UserSupabase
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

fun UsersData.toRemote(): UserSupabase =
    UserSupabase(
        userId = supabaseId,
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

fun UserSupabase.toDomain(): UsersData =
    UsersData(
        supabaseId = userId,
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