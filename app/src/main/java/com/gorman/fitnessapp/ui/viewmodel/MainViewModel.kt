package com.gorman.fitnessapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.usecases.GetUserIdUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.UserCheckState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getUserIdUseCase: GetUserIdUseCase
): ViewModel() {
    private val _userCheckState = mutableStateOf<UserCheckState>(UserCheckState.Idle)
    val userCheckState: State<UserCheckState> = _userCheckState

    fun checkUserExisting() {
        _userCheckState.value = UserCheckState.Loading
        viewModelScope.launch {
            try {
                _userCheckState.value =
                    if (getUserIdUseCase()?.isNotEmpty() == true) UserCheckState.Success
                    else UserCheckState.NotExists
                logger.d("VIEWMODEL", getUserIdUseCase().toString())
            } catch (e: Exception) {
                logger.d("CHECK USER VIEWMODEL", "${e.message}")
                _userCheckState.value = UserCheckState.Error(e.message ?: "Ошибка при извлечении данных")
            }
        }
    }
}