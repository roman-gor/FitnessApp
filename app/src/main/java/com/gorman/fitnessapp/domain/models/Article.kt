package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int = 0,
    val imageUrl: String,
    val title: String,
    val text: String
)