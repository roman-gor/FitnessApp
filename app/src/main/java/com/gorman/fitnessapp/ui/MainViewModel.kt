package com.gorman.fitnessapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.data.models.UsersDataEntity
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel(){
    private val _usersState: MutableState<List<UsersDataEntity>> = mutableStateOf(emptyList())
    val usersState: MutableState<List<UsersDataEntity>> = _usersState

    fun getAllUsers() {
        viewModelScope.launch {
            _usersState.value = databaseRepository.getAllUsers()
        }
    }

    fun addUser(user: UsersDataEntity) {
        viewModelScope.launch {
            databaseRepository.addUser(user)
        }
    }
}