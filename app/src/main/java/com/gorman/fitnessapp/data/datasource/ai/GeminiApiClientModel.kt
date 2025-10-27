package com.gorman.fitnessapp.data.datasource.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.RequestOptions
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class GeminiApiClientModel @Inject constructor(
    private val apiKey: String
): AiApiClient {

    private val model: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-pro",
        apiKey = apiKey,
        requestOptions = RequestOptions(
            timeout = 120.seconds
        )
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