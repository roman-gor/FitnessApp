package com.gorman.fitnessapp.ui.screens.workout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.GeneralBackButton
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.viewmodel.ProgramViewModel

@Composable
fun ProgramInfoScreen(
    onBackPage: () -> Unit,
    onProfileClick: (UsersData) -> Unit,
    userData: UsersData?
) {
    val programViewModel: ProgramViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        programViewModel.prepareProgramData()
    }
    val program by programViewModel.programTemplateState
    val programExercises by programViewModel.programExercisesState
    val uiState by programViewModel.programUiState
    val groupedByDay = programExercises.groupBy { it.dayOfWeek }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_color))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            onBackPage = { onBackPage() },
            onProfileClick = { onProfileClick(it) },
            userData = userData
        )
        Spacer(modifier = Modifier.height(25.dp))
        program?.name?.let { ProgramInfoCard(it) }
        Text(
            text = stringResource(R.string.exercisesPerDay_header),
            fontFamily = mulishFont(),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = colorResource(R.color.meet_text)
        )
        groupedByDay.forEach { (day, exercises) ->
            val totalExercises = exercises.size
            val duration = exercises.sumOf { it.durationSec } / 60
            val calories = exercises.sumOf { it.caloriesBurned?.toDouble() ?: 0.0 }.toInt()
            val iconRes = when (day) {
                "Понедельник" -> R.drawable.program_item_image1
                "Вторник" -> R.drawable.program_item_image2
                "Среда" -> R.drawable.program_item_image3
                else -> R.drawable.program_item_image1
            }
            ProgramItemCard(
                programDay = day,
                durationTotal = duration,
                caloriesTotal = calories,
                exercisesQuantity = totalExercises,
                imageBg = iconRes
            )
        }
    }
}

@Composable
fun Header(
    onBackPage: () -> Unit,
    onProfileClick: (UsersData) -> Unit,
    userData: UsersData?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 28.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            GeneralBackButton(
                onClick = { onBackPage() },
                text = stringResource(R.string.workout),
                textColor = colorResource(R.color.picker_wheel_bg)
            )
        }
        IconButton (
            onClick = {
                userData?.let { onProfileClick(it)  } },
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.profile_icon),
                contentDescription = "Profile Icon",
                tint = colorResource(R.color.picker_wheel_bg),
                modifier = Modifier
                    .scale(1.4f)
                    .padding(8.dp))
        }
    }
}

@Composable
fun ProgramInfoCard(
    programName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(colorResource(R.color.picker_wheel_bg)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.bg_color)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(192.dp)
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
                            colorResource(R.color.black).copy(alpha = 0.5f)
                        )
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = programName,
                        color = colorResource(R.color.meet_text),
                        fontFamily = mulishFont(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProgramItemCard(
    programDay: String = "Понедельник",
    durationTotal: Int = 60,
    caloriesTotal: Int = 150,
    exercisesQuantity: Int = 5,
    @DrawableRes imageBg: Int
) {
    val dayOfWeek = when (programDay) {
        "Понедельник" -> stringResource(R.string.monday)
        "Среда" -> stringResource(R.string.wednesday)
        "Пятница" -> stringResource(R.string.friday)
        else -> programDay
    }
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dayOfWeek,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.bg_color),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.clock_icon),
                        tint = colorResource(R.color.bg_color),
                        contentDescription = null,
                        modifier = Modifier.scale(1.2f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$durationTotal ${minutesSuffix(durationTotal)}",
                        fontFamily = mulishFont(),
                        color = colorResource(R.color.bg_color),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.fire_icon),
                        tint = colorResource(R.color.bg_color),
                        contentDescription = null,
                        modifier = Modifier.scale(1.3f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$caloriesTotal ${stringResource(R.string.calories)}",
                        fontFamily = mulishFont(),
                        color = colorResource(R.color.bg_color),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.run_icon),
                        tint = colorResource(R.color.bg_color),
                        contentDescription = null,
                        modifier = Modifier.scale(1.3f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$exercisesQuantity ${exercisesSuffix(exercisesQuantity)}",
                        fontFamily = mulishFont(),
                        color = colorResource(R.color.bg_color),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(imageBg),
                contentDescription = null,
                modifier = Modifier
                    .height(145.dp)
                    .width(185.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun minutesSuffix(minutes: Int): String {
    val lastTwoDigits = minutes % 100
    val lastDigit = minutes % 10
    return when {
        lastTwoDigits in 11..14 -> stringResource(R.string.minutes)
        lastDigit == 1 -> stringResource(R.string.minute)
        lastDigit in 2..4 -> stringResource(R.string.minute2)
        else -> stringResource(R.string.minutes)
    }
}

@Composable
fun exercisesSuffix(exercisesQuantity: Int): String {
    val lastTwoDigits = exercisesQuantity % 100
    val lastDigit = exercisesQuantity % 10
    return when {
        lastTwoDigits in 11..14 -> stringResource(R.string.exercises)
        lastDigit == 1 -> stringResource(R.string.exercise)
        lastDigit in 2..4 -> stringResource(R.string.exercise2)
        else -> stringResource(R.string.exercises)
    }
}