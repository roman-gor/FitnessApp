package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.usecases.SetProgramIdUseCase
import com.gorman.fitnessapp.domain.usecases.SetUserIdUseCase
import com.gorman.fitnessapp.ui.states.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val setUserIdUseCase: SetUserIdUseCase,
    private val setProgramIdUseCase: SetProgramIdUseCase
): ViewModel() {

    private val _uiEvent = MutableSharedFlow<RegisterUiState>()
    val uiEvent = _uiEvent

    fun updateUserData(userData: UsersData) {
        viewModelScope.launch {

        }
    }

    fun logoutFromDevice() {
        viewModelScope.launch {
            setUserIdUseCase("")
            setProgramIdUseCase("")
            _uiEvent.emit(RegisterUiState.Logout)
        }
    }

    fun deleteAccount(id: String) {
        viewModelScope.launch {

        }
    }
}