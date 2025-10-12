package com.gorman.fitnessapp.data.datasource.ai

interface AiApiClient {
    suspend fun completion(prompt: String): String
}