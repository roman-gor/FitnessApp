package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.ArticleFirebase
import com.gorman.fitnessapp.data.models.room.ArticleEntity
import com.gorman.fitnessapp.domain.models.Article

fun ArticleFirebase.toDomain(): Article =
    Article(
        title = title,
        text = text,
        imageUrl = imageUrl
    )

fun ArticleEntity.toDomain(): Article =
    Article(
        id = id,
        title = title,
        text = text,
        imageUrl = imageUrl
    )

fun Article.toEntity(): ArticleEntity =
    ArticleEntity(
        title = title,
        text = text,
        imageUrl = imageUrl
    )