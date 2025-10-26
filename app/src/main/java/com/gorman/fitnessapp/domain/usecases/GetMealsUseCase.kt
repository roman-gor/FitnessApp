package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
) {
    /**
     * Получает актуальный список приемов пищи (блюд), реализуя стратегию кэширования "сначала локально".
     *
     * Сначала UseCase пытается загрузить список блюд из локальной базы данных (Room).
     * Если локальная база пуста, он обращается к удаленному хранилищу (Firebase),
     * сохраняет полученные данные в локальную базу для последующих запросов и возвращает их.
     * Это обеспечивает быстрый доступ к данным при повторных вызовах и возможность работы в офлайн-режиме.
     */
    suspend operator fun invoke(): List<Meal> {
        val localMeals = databaseRepository.getMeals()
        return localMeals.ifEmpty {
            val remoteMeals = firebaseRepository.getMeals()
            databaseRepository.insertMeals(remoteMeals)
            remoteMeals
        }
    }
}