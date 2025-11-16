package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.usecases.GetUserIdUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.UserCheckState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getUserIdUseCase: GetUserIdUseCase
): ViewModel() {
    private val _userCheckState = MutableStateFlow<UserCheckState>(UserCheckState.Idle)
    val userCheckState: StateFlow<UserCheckState> = _userCheckState

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