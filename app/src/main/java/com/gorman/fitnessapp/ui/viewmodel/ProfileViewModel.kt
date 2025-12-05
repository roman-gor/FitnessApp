package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.usecases.DeleteProgressAndHistoryUseCase
import com.gorman.fitnessapp.domain.usecases.DeleteUserUseCase
import com.gorman.fitnessapp.domain.usecases.SetMealsIdUseCase
import com.gorman.fitnessapp.domain.usecases.SetProgramIdUseCase
import com.gorman.fitnessapp.domain.usecases.SetUserIdUseCase
import com.gorman.fitnessapp.domain.usecases.UpdateUserUseCase
import com.gorman.fitnessapp.ui.states.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val setUserIdUseCase: SetUserIdUseCase,
    private val setProgramIdUseCase: SetProgramIdUseCase,
    private val setMealsIdUseCase: SetMealsIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val deleteProgressAndHistoryUseCase: DeleteProgressAndHistoryUseCase
): ViewModel() {

    private val _uiEvent = MutableSharedFlow<RegisterUiState>()
    val uiEvent = _uiEvent

    fun updateUserData(userData: UsersData) {
        viewModelScope.launch {
            updateUserUseCase(userData)
        }
    }

    fun logoutFromDevice() {
        viewModelScope.launch {
            setUserIdUseCase("")
            setProgramIdUseCase("")
            setMealsIdUseCase("")
            deleteProgressAndHistoryUseCase()
            _uiEvent.emit(RegisterUiState.Logout)
        }
    }

    fun deleteAccount(user: UsersData) {
        viewModelScope.launch {
            setUserIdUseCase("")
            setProgramIdUseCase("")
            setMealsIdUseCase("")
            deleteProgressAndHistoryUseCase()
            _uiEvent.emit(RegisterUiState.Logout)
            deleteUserUseCase(user)
        }
    }
}