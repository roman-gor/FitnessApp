package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class PushProgramsToFirebaseUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    
}