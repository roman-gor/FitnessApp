package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val supabaseRepository: SupabaseRepository
) {
    /**
     * Получает актуальный список упражнений, реализуя стратегию кэширования "сначала локально".
     *
     * Сначала UseCase пытается загрузить упражнения из локальной базы данных (Room).
     * Если локальная база пуста, он обращается к удаленному хранилищу (Firebase),
     * сохраняет полученные данные в локальную базу для последующих запросов и возвращает их.
     * Это обеспечивает быстрый доступ к данным при повторных вызовах и работу в офлайн-режиме.
     */
    suspend operator fun invoke(): List<Exercise>? {
        val localExercises = databaseRepository.getExercises()
        return localExercises?.ifEmpty {
            val remoteExercises = supabaseRepository.getExercises()
            databaseRepository.insertExercises(remoteExercises)
            remoteExercises
        }
    }
}