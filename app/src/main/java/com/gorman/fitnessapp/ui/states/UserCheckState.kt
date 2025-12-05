package com.gorman.fitnessapp.ui.states

sealed class UserCheckState {
    object Idle: UserCheckState()
    object Loading: UserCheckState()
    object NotExists: UserCheckState()
    object Success: UserCheckState()
    data class Error(val message: String) : UserCheckState()
}