package com.gorman.fitnessapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel(){
    private val _usersState: MutableState<List<UsersData>> = mutableStateOf(emptyList())
    val usersState: MutableState<List<UsersData>> = _usersState

    fun getAllUsers() {
        viewModelScope.launch {
            _usersState.value = databaseRepository.getAllUsers()
        }
    }

    fun addUser(user: UsersData) {
        viewModelScope.launch {
            databaseRepository.addUser(user)
        }
    }
}