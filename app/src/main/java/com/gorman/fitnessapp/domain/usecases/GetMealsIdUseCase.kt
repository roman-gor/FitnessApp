package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMealsIdUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Int =
        settingsRepository.mealIdFlow.first()
}