package com.gorman.fitnessapp.data.datasource.remote

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface Storage {
    fun uploadProfileImage(uri: Uri): Flow<Result<String>>
    fun downloadProfileImage(url: String)
}