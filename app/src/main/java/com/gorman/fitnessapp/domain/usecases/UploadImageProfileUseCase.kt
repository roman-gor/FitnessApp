package com.gorman.fitnessapp.domain.usecases

import android.net.Uri
import com.gorman.fitnessapp.data.datasource.remote.Storage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageProfileUseCase @Inject constructor(
    private val storage: Storage
) {
    operator fun invoke(uri: Uri): Flow<Result<String>> {
        return storage.uploadProfileImage(uri)
    }
}