package com.gorman.fitnessapp.ui.screens.workout

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.ui.components.Header
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.viewmodel.ProgramViewModel

@Composable
fun ProgramByDayScreen(
    onBackPage: () -> Unit,
    day: String,
    onExerciseProgramClick: (Exercise) -> Unit,
    onStartProgram: (List<ProgramExercise>) -> Unit
) {
    val programViewModel: ProgramViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        programViewModel.prepareProgramData()
    }
    val programExercises by programViewModel.programExercisesState.collectAsState()
    val exercisesList by programViewModel.exercisesListState.collectAsState()
    val programExercisesByDay = programExercises.filter { it.dayOfWeek == day }
    val totalExercises = programExercisesByDay.size
    val duration = programExercisesByDay.sumOf { it.durationSec * it.sets } / 60
    val calories = programExercisesByDay.sumOf { it.caloriesBurned?.toDouble() ?: 0.0 }.toInt()
    val dayStr = when (day) {
        "Понедельник" -> R.string.monday
        "Среда" -> R.string.wednesday
        "Пятница" -> R.string.friday
        else -> R.string.monday
    }
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
                text = stringResource(dayStr)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgramByDayCard(
                    durationTotal = duration,
                    caloriesTotal = calories,
                    exercisesTotal = totalExercises,
                    onStartProgram = { onStartProgram(programExercisesByDay) }
                )
                programExercisesByDay.forEach { programExercise ->
                    val exerciseId = programExercise.exerciseId
                    ExerciseCard(
                        name = exercisesList[exerciseId].name,
                        repetitions = programExercise.repetitions,
                        onExerciseClick = { onExerciseProgramClick(exercisesList[exerciseId]) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProgramByDayCard(
    durationTotal: Int,
    caloriesTotal: Int,
    exercisesTotal: Int,
    onStartProgram: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.bg_color)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.program_default_image),
                contentDescription = "Woman exercising in a gym",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        colorResource(R.color.black).copy(alpha = 0.65f)
                    )
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.clock_icon),
                            tint = colorResource(R.color.white),
                            contentDescription = null,
                            modifier = Modifier.scale(1.2f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$durationTotal ${minutesSuffix(durationTotal)}",
                            fontFamily = mulishFont(),
                            color = colorResource(R.color.white),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.fire_icon),
                            tint = colorResource(R.color.white),
                            contentDescription = null,
                            modifier = Modifier.scale(1.3f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$caloriesTotal ${stringResource(R.string.calories)}",
                            fontFamily = mulishFont(),
                            color = colorResource(R.color.white),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.run_icon),
                            tint = colorResource(R.color.white),
                            contentDescription = null,
                            modifier = Modifier.scale(1.3f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$exercisesTotal ${exercisesSuffix(exercisesTotal)}",
                            fontFamily = mulishFont(),
                            color = colorResource(R.color.white),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.matchParentSize()
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    PlayButton { onStartProgram() }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(
    name: String,
    repetitions: Int,
    onExerciseClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable(onClick = { onExerciseClick() }),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(36.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.play_icon),
                contentDescription = "StartExercise",
                tint = colorResource(R.color.meet_text),
                modifier = Modifier.scale(1.2f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                fontFamily = mulishFont(),
                color = colorResource(R.color.bg_color),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${stringResource(R.string.repetitions_text)} ${repetitions}x",
                fontFamily = mulishFont(),
                color = colorResource(R.color.font_purple_color),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}
