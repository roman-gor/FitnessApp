package com.gorman.fitnessapp.ui.screens.workout

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.ui.components.Header
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.ProgramHistoryState
import com.gorman.fitnessapp.ui.viewmodel.ProgramViewModel

@Composable
fun ProgramRunScreen(
    exercisesProgram: List<ProgramExercise>,
    onBackPage: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val programViewModel: ProgramViewModel = hiltViewModel()
    val uiState by programViewModel.historyInsertState.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0) { exercisesProgram.size + 1 }
    LaunchedEffect(Unit) {
        programViewModel.getExercisesList()
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == exercisesProgram.size) {
                programViewModel.markProgramAsCompleted(exercisesProgram)
            }
        }
    }
    Log.d("Prorams", exercisesProgram.toString())
    val exercises by programViewModel.exercisesListState.collectAsState()
    when (uiState) {
        ProgramHistoryState.Prepared -> {
            DefaultScreen(
                pagerState = pagerState,
                exercisesProgram = exercisesProgram,
                onBackPage = onBackPage,
                exercises = exercises
            )
        }
        ProgramHistoryState.Success -> {
            WorkoutCompleteScreen { onNavigateToHome() }
        }
        ProgramHistoryState.Loading -> LoadingStub()
        else -> {}
    }
}

@Composable
fun DefaultScreen(
    pagerState: PagerState,
    exercisesProgram: List<ProgramExercise>,
    onBackPage: () -> Unit,
    exercises: List<Exercise>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(colorResource(R.color.bg_color))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(
                onBackPage = onBackPage,
                text = stringResource(R.string.exercise)
            )
            HorizontalPager(state = pagerState) { page ->
                if (page < exercisesProgram.size) {
                    val exerciseId = exercisesProgram[page].exerciseId
                    val exercise = exercises.first { it.id == exerciseId }
                    ExerciseItem(
                        exercise = exercise
                    )
                } else {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(colorResource(R.color.bg_color))
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: Exercise
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isPlaying by remember { mutableStateOf(false) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(exercise.videoUrl!!)
            setMediaItem(mediaItem)
            prepare()
            volume = 0f
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_ALL
        }
    }
    Log.d("Exercise", exercise.toString())
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                exoPlayer.pause()
                isPlaying = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.picker_wheel_bg)),
            contentAlignment = Alignment.Center
        ) {
            VideoItem(exoPlayer) {
                if (isPlaying)
                    exoPlayer.pause()
                else
                    exoPlayer.play()
                isPlaying = !isPlaying
            }
            if (!isPlaying) {
                PlayButton {
                    isPlaying = !isPlaying
                }
            }
        }
        ExerciseDescription(exercise = exercise)
    }
}

@Composable
fun WorkoutCompleteScreen(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_color)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.complete_program),
                fontFamily = mulishFont(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { onClick() },
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(36.dp),
                modifier = Modifier
                    .width(200.dp)
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                ),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
            ) {
                Text(text = stringResource(R.string.to_home),
                    fontFamily = mulishFont(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp))
            }
        }
    }
}