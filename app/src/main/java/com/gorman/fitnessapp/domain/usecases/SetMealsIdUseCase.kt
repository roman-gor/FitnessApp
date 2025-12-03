package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.SettingsRepository
import javax.inject.Inject

class SetMealsIdUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(id: String) {
        settingsRepository.setMealId(id)
    }
}