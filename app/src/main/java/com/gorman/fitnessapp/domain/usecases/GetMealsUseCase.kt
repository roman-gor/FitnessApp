package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val supabaseRepository: SupabaseRepository
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
            val remoteMeals = supabaseRepository.getMeals()
            databaseRepository.insertMeals(remoteMeals)
            remoteMeals
        }
    }
}