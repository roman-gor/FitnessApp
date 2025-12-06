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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.models.WorkoutHistory
import com.gorman.fitnessapp.ui.components.DatesGrid
import com.gorman.fitnessapp.ui.components.Header
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.components.MonthPicker
import com.gorman.fitnessapp.ui.components.WeekdaysRow
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.ProgressUiState
import com.gorman.fitnessapp.ui.viewmodel.ProgressViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProgressScreen(
    onBackPage: () -> Unit
) {
    val progressViewModel: ProgressViewModel = hiltViewModel()
    var currentDate by remember { mutableStateOf(LocalDate.now(ZoneId.of("Europe/Moscow"))) }
    LaunchedEffect(Unit) {
        progressViewModel.prepareData()
        progressViewModel.getExercises()
    }
    val uiState by progressViewModel.progressState.collectAsState()
    val usersData by progressViewModel.usersData.collectAsState()
    val exercises by progressViewModel.exercisesState.collectAsState()
    when(val state = uiState) {
        is ProgressUiState.Error ->
            GenerationDefault(
                onClick = { progressViewModel.prepareData() },
                text = stringResource(R.string.error_progress),
                backgroundImage = R.drawable.meals_img,
                buttonText = stringResource(R.string.try_again)
            )
        ProgressUiState.Idle -> LoadingStub()
        ProgressUiState.Loading -> LoadingStub()
        is ProgressUiState.Success ->
            DefaultProgressScreen(
                onBackPage = onBackPage,
                currentDate = currentDate,
                usersData = usersData,
                exercises = exercises,
                onDateChange = { currentDate = it },
                historyList = state.progressHistory
            )
    }
}

@Composable
fun ProgressContent (
    onBackPage: () -> Unit,
    usersData: UsersData?,
    exercises: List<Exercise>,
    currentDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    monthNameFormatted: String,
    onDatePickerClick: () -> Unit,
    filterHistory: List<WorkoutHistory>
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
                text = stringResource(R.string.progress_tracking)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.picker_wheel_bg))
                ) {
                    UserDataScreen(usersData)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.choose_date),
                        fontFamily = mulishFont(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.meet_text)
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        text = stringResource(processMonthName(monthNameFormatted)),
                        fontFamily = mulishFont(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        modifier = Modifier.clickable(onClick = onDatePickerClick)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Calendar(
                        currentDate = currentDate,
                        onDateSelected = { date -> onDateChange(date) }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.activities),
                    fontFamily = mulishFont(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = colorResource(R.color.meet_text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(16.dp))
                HistoryList(
                    filterHistory = filterHistory,
                    exercises = exercises)
            }
        }
    }
}

@Composable
fun DefaultProgressScreen(
    onBackPage: () -> Unit,
    usersData: UsersData?,
    exercises: List<Exercise>,
    currentDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    historyList: List<WorkoutHistory>
) {
    var visibleDatePicker by remember { mutableStateOf(false) }
    val formatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)
    val monthNameFormatted = currentDate.format(formatter)
    val currentDateLong = currentDate.atStartOfDay(ZoneOffset.systemDefault()).toEpochSecond()
    val filterHistory = remember(currentDate, historyList) {
        historyList.filter { it.date == currentDateLong * 1000 }
    }
    ProgressContent(
        onBackPage = onBackPage,
        usersData = usersData,
        exercises = exercises,
        monthNameFormatted = monthNameFormatted,
        onDatePickerClick = { visibleDatePicker = true },
        currentDate = currentDate,
        onDateChange = onDateChange,
        filterHistory = filterHistory
    )
    MonthPicker(
        visible = visibleDatePicker,
        currentMonth = currentDate.monthValue - 1,
        currentYear = currentDate.year,
        confirmButtonClicked = { month, year ->
            onDateChange(LocalDate.of(year, month, 1))
            visibleDatePicker = false
        },
        cancelClicked = {
            visibleDatePicker = false
        }
    )
}

@Composable
fun HistoryList(
    filterHistory: List<WorkoutHistory>,
    exercises: List<Exercise>
) {
    filterHistory.forEach { history ->
        val exercise = exercises[history.exerciseId]
        val duration = history.repsCompleted * history.setsCompleted
        val complexity = exercise.complexity
        HistoryListItem(
            exercise,
            complexity ?: 1,
            duration)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun HistoryListItem(
    exercise: Exercise,
    complexity: Int,
    duration: Int
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(90.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                modifier = Modifier.height(55.dp)
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_run),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(55.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.fire_icon),
                            tint = colorResource(R.color.picker_wheel_bg),
                            contentDescription = null,
                            modifier = Modifier.scale(1.4f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$complexity ${stringResource(R.string.level_complex)}",
                            fontFamily = mulishFont(),
                            lineHeight = 15.sp,
                            color = colorResource(R.color.black),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = exercise.name,
                        fontFamily = mulishFont(),
                        lineHeight = 15.sp,
                        color = colorResource(R.color.black),
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.padding(end = 8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.duration),
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.font_purple_color),
                    fontSize = 14.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${duration}x",
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.black),
                    lineHeight = 15.sp,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

fun processMonthName(month: String): Int {
    return when (month) {
        "January" -> R.string.January
        "February" -> R.string.February
        "March" -> R.string.March
        "April" -> R.string.April
        "May" -> R.string.May
        "June" -> R.string.June
        "July" -> R.string.July
        "August" -> R.string.August
        "September" -> R.string.August
        "October" -> R.string.October
        "November" -> R.string.November
        "December" -> R.string.December
        else -> R.string.month
    }
}

@Composable
fun Calendar(
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedDate by remember(currentDate) {
        mutableStateOf<LocalDate?>(currentDate)
    }
    val displayMonth = currentDate.withDayOfMonth(1)
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF2E2D38))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        WeekdaysRow()
        DatesGrid(
            currentMonth = displayMonth,
            selectedDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date
                onDateSelected(date)
            }
        )
    }
}

@Composable
fun UserDataScreen(
    usersData: UsersData?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            usersData?.name?.let {
                Text(
                    text = it,
                    fontFamily = mulishFont(),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            usersData?.age?.let {
                Text(
                    text = "${stringResource(R.string.age)}: $it",
                    fontFamily = mulishFont(),
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Row (
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier
                        .width(4.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .height(28.dp)
                        .background(colorResource(R.color.meet_text)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        usersData?.weight?.let {
                            Text(
                                text = "${it.toInt()} ${stringResource(R.string.kg)}",
                                fontFamily = mulishFont(),
                                fontSize = 12.sp,
                                lineHeight = 15.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = stringResource(R.string.weight),
                            fontFamily = mulishFont(),
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Row (
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier
                        .width(4.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .height(28.dp)
                        .background(colorResource(R.color.meet_text)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        usersData?.height?.let {
                            Text(
                                text = "${it.toString().format("%", it*100)} ${stringResource(R.string.cm)}",
                                fontFamily = mulishFont(),
                                fontSize = 12.sp,
                                lineHeight = 15.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = stringResource(R.string.height),
                            fontFamily = mulishFont(),
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(24.dp))
        if (usersData?.photoUrl?.isNotEmpty() == true) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(usersData.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Firebase Image",
                placeholder = painterResource(R.drawable.placeholder_ava),
                error = painterResource(R.drawable.placeholder_ava),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(125.dp),
                contentScale = ContentScale.Crop)
        }
        else
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .build(),
                contentDescription = "Firebase Image",
                placeholder = painterResource(R.drawable.placeholder_ava),
                error = painterResource(R.drawable.placeholder_ava),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(125.dp),
                contentScale = ContentScale.Crop)
    }
}