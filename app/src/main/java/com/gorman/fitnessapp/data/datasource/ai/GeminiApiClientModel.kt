package com.gorman.fitnessapp.data.datasource.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class GeminiApiClientModel @Inject constructor(
    private val apiKey: String
): AiApiClient {

    private val model: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey
    )

    override suspend fun completion(prompt: String): String {
        return withContext(Dispatchers.IO) {
            val content = content {
                text(prompt)
            }
            val response = model.generateContent(content)
            response.text ?: "Не удалось сгенерировать ответ."
        }
    }

}