package com.gorman.fitnessapp.data.datasource.remote

import android.net.Uri
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.gorman.fitnessapp.logger.AppLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StorageImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val logger: AppLogger
): Storage {
    override fun uploadProfileImage(uri: Uri): Flow<Result<String>> = callbackFlow {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl
                .addOnSuccessListener { downloadUrl ->
                    trySend(Result.success(downloadUrl.toString()))
                    logger.d("FIREBASE_STORAGE", "Успешно. Загрузка изображения в Storage")
                    close()
                }
                .addOnFailureListener { exception ->
                    trySend(Result.failure(exception))
                    logger.d("FIREBASE_STORAGE", "Ошибка. Загрузка изображения в Storage")
                    close()
                }
        }.addOnFailureListener { exception ->
            trySend(Result.failure(exception))
            logger.d("FIREBASE_STORAGE", "Ошибка. Загрузка изображения в Storage")
            close()
        }
        awaitClose {
            uploadTask.cancel()
        }
    }


    override fun downloadProfileImage(url: String) {
        TODO("Not yet implemented")
    }
}