package com.gorman.fitnessapp.ui.screens.nutrition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.screens.onboarding.InfoScreenTemplate

@Composable
fun DefaultGenMealsScreen(
    onNextPage: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        InfoScreenTemplate(
            onNextPage = onNextPage,
            buttonTitle = R.string.know_plan,
            icon = R.drawable.info_screen_icon_2,
            text = R.string.generate_meals_plan,
            image = R.drawable.meals_img
        )
    }
}

@Composable
@Preview
fun PreviewGen() {
    DefaultGenMealsScreen {  }
}