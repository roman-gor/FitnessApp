package com.gorman.fitnessapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.ui.components.GeneralBackButton
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.ExercisesUiState
import com.gorman.fitnessapp.ui.viewmodel.ResourcesViewModel

@Composable
fun ResourcesScreen(
    onBackPage: () -> Unit,
    onItemClick: (Exercise) -> Unit
) {
    val resourcesViewModel: ResourcesViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        resourcesViewModel.loadExercises()
    }
    val exercisesState by resourcesViewModel.exercisesState.collectAsState()
    when (val state = exercisesState) {
        is ExercisesUiState.Error -> {
            ExercisesErrorScreen { resourcesViewModel.loadExercises() }
        }
        ExercisesUiState.Idle -> {}
        ExercisesUiState.Loading -> {
            LoadingStub()
        }
        is ExercisesUiState.Success -> {
            ExercisesSuccessScreen(
                onBackPage = onBackPage,
                onItemClick = onItemClick,
                exercises = state.list
            )
        }
    }
}

@Composable
fun ExercisesSuccessScreen(
    onBackPage: () -> Unit,
    onItemClick: (Exercise) -> Unit,
    exercises: List<Exercise>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_color))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 28.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                GeneralBackButton(
                    onClick = { onBackPage() },
                    text = stringResource(R.string.resources),
                    textColor = colorResource(R.color.picker_wheel_bg)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.exercise2),
                fontFamily = mulishFont(),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 24.dp),
                color = colorResource(R.color.meet_text)
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(exercises) { item ->
                    ExerciseItem(exercise = item) {
                        onItemClick(it)
                    }
                }
            }
        }
    }
}



@Composable
fun ExerciseItem(
    exercise: Exercise,
    onItemClick: (Exercise) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = {
                onItemClick(exercise)
            })
            .clip(RoundedCornerShape(24.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(exercise.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Firebase Image",
            placeholder = painterResource(R.drawable.articles_placeholder),
            error = painterResource(R.drawable.articles_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
                .height(140.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    colorResource(R.color.black).copy(alpha = 0.6f)
                )
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = exercise.name,
                color = colorResource(R.color.white),
                fontFamily = mulishFont(),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun ExercisesErrorScreen(
    onTryAgainClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_color)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.exercises_error),
                color = colorResource(R.color.white),
                fontFamily = mulishFont(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.try_again),
                color = colorResource(R.color.white),
                fontFamily = mulishFont(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable(onClick = {
                        onTryAgainClick()
                    })
            )
        }
    }
}