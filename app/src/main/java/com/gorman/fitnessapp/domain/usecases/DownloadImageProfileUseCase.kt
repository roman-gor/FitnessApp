package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.data.datasource.remote.Storage
import javax.inject.Inject

class DownloadImageProfileUseCase @Inject constructor(
    private val storage: Storage
) {
    operator fun invoke(url: String) {
        storage.downloadProfileImage(url)
    }
}