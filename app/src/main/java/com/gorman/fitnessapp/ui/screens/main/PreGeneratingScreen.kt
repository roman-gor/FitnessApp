package com.gorman.fitnessapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.gorman.fitnessapp.R

@Composable
fun PreGeneratingScreen(
    destination: String,
    onProgramGenerate: () -> Unit,
    onMealGenerate: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.bg_color)))
    when (destination) {
        "program" -> { onProgramGenerate() }
        "meal" -> { onMealGenerate() }
    }
}