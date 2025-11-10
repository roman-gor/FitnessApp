package com.gorman.fitnessapp.domain.models

data class Article(
    val id: Int = 0,
    val imageUrl: String,
    val title: String,
    val text: String
)