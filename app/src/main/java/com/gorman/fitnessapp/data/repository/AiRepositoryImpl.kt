package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.ai.GeminiGenerator
import com.gorman.fitnessapp.data.datasource.ai.dto.ProgramDto
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val generator: GeminiGenerator
): AiRepository {
    override suspend fun generatePrograms(usersData: UsersData, availableExercises: Map<Int, String>): List<Program> {
        val response = generator.generateWorkoutProgram(userData = usersData.toEntity(), availableExercises = availableExercises)
        val programDto: List<ProgramDto> = Json.decodeFromString(response)
        return programDto.map { it.toDomain() }
    }
}