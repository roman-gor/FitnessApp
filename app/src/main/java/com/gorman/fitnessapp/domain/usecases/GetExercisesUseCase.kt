package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
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
            val remoteExercises = firebaseRepository.getExercises()
            databaseRepository.insertExercises(remoteExercises)
            remoteExercises
        }
    }
}