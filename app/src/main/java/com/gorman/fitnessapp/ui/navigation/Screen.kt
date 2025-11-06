package com.gorman.fitnessapp.ui.navigation

sealed class Screen() {
    sealed class SetupScreen(val route: String): Screen() {
        object Welcome: SetupScreen("welcome")
        object Gender: SetupScreen("gender")
        object Age: SetupScreen("age")
        object Weight: SetupScreen("weight")
        object Height: SetupScreen("height")
        object Goal: SetupScreen("goal")
        object ActivityLevel: SetupScreen("activity_level")
        object ProfileSetup: SetupScreen("profile_setup")
    }
}
