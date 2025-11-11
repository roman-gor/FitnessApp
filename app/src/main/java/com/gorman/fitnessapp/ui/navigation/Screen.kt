package com.gorman.fitnessapp.ui.navigation

import com.gorman.fitnessapp.R

sealed class Screen() {
    sealed class SetupScreen(val route: String): Screen() {
        object SignIn: SetupScreen("sign_in")
        object Start: SetupScreen("start")
        object Info: SetupScreen("info")
        object Welcome: SetupScreen("welcome")
        object Gender: SetupScreen("gender")
        object Age: SetupScreen("age")
        object Weight: SetupScreen("weight")
        object DesiredWeight: SetupScreen("desired_weight")
        object Height: SetupScreen("height")
        object Goal: SetupScreen("goal")
        object ActivityLevel: SetupScreen("activity_level")
        object ProfileSetup: SetupScreen("profile_setup")
    }

    sealed class BottomScreen(val route: String, val icon: Int, val title: Int): Screen() {
        object Home: BottomScreen("home", R.drawable.home_bottom_icon, R.string.home)
        object Resources: BottomScreen("res", R.drawable.res_bottom_icon, R.string.res)
    }

    sealed class GeneralHomeScreen(val route: String): Screen() {
        object Profile: GeneralHomeScreen("profile")
        object Workout: GeneralHomeScreen("workout")
        object Progress: GeneralHomeScreen("progress")
        object Nutrition: GeneralHomeScreen("nutrition")
    }

    sealed class ProfileItemsScreen(val icon: Int, val title: Int): Screen() {
        object Edit: ProfileItemsScreen(R.drawable.profile_edit_icon, R.string.edit)
        object Settings: ProfileItemsScreen(R.drawable.settings_icon, R.string.settings)
        object Logout: ProfileItemsScreen(R.drawable.logout_icon, R.string.logout)
    }
    companion object {
        val bItems = listOf(
            BottomScreen.Home,
            BottomScreen.Resources
        )
        val pItems = listOf(
            ProfileItemsScreen.Edit,
            ProfileItemsScreen.Settings,
            ProfileItemsScreen.Logout
        )
    }
}
