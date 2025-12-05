package com.gorman.fitnessapp.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun uploadProfileImage(uri: Uri): Flow<Result<String>>
    fun downloadProfileImage(url: String)
}