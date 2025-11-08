package com.gorman.fitnessapp.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.usecases.SaveNewUserUseCase
import com.gorman.fitnessapp.domain.usecases.SetUserIdUseCase
import com.gorman.fitnessapp.domain.usecases.UploadImageProfileUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val saveNewUserUseCase: SaveNewUserUseCase,
    private val uploadImageProfileUseCase: UploadImageProfileUseCase,
    private val logger: AppLogger
): ViewModel() {

    private val _registerUiState = mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
    val registerUiState: State<RegisterUiState> = _registerUiState

    fun registerUser(uri: Uri?, usersData: UsersData) {
        viewModelScope.launch {
            _registerUiState.value = RegisterUiState.Loading
            try {
                if (uri != null) {
                    uploadImageProfileUseCase(uri).collect { result->
                        result.onSuccess {
                            saveNewUserUseCase(usersData.copy(photoUrl = result.getOrNull()))
                            _registerUiState.value = RegisterUiState.Success
                        }.onFailure { e->
                            logger.e("IMAGE FIREBASE", "${e.message}")
                            _registerUiState.value = RegisterUiState.Error(e.message.toString())
                        }
                    }
                }
                else {
                    saveNewUserUseCase(usersData)
                    _registerUiState.value = RegisterUiState.Success
                }
            } catch (e: IllegalStateException) {
                logger.e("REGISTER", "Ошибка регистрации: ${e.message}")
                _registerUiState.value = RegisterUiState.Error(e.message ?: "Ошибка регистрации")
            } catch (e: Exception) {
                logger.e("REGISTER", "Неожиданная ошибка: ${e.message}")
                _registerUiState.value = RegisterUiState.Error("Что-то пошло не так: ${e.message}")
            }
        }
    }

    fun resetState() {
        _registerUiState.value = RegisterUiState.Idle
    }
}