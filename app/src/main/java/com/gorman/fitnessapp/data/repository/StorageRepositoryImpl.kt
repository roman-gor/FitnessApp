package com.gorman.fitnessapp.data.repository

import android.net.Uri
import com.gorman.fitnessapp.data.datasource.remote.Storage
import com.gorman.fitnessapp.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: Storage
): StorageRepository {
    override fun uploadProfileImage(uri: Uri): Flow<Result<String>> {
        return storage.uploadProfileImage(uri)
    }

    override fun downloadProfileImage(url: String) {
        return storage.downloadProfileImage(url)
    }
}