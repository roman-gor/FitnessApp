package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): List<Article> {
        val localArticles = databaseRepository.getArticles()
        return localArticles.ifEmpty {
            val remoteArticles = firebaseRepository.getArticles()
            databaseRepository.insertArticles(remoteArticles)
            remoteArticles
        }
    }
}