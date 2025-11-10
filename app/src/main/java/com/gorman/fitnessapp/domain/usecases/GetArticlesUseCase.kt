package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): List<Article> {
        return firebaseRepository.getArticles()
    }
}