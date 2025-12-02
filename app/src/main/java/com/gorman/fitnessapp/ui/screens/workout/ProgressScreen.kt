package com.gorman.fitnessapp.ui.screens.workout

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UserProgress
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
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProgressScreen(
    onBackPage: () -> Unit
) {
    val progressViewModel: ProgressViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        progressViewModel.prepareData()
    }
    val uiState by progressViewModel.progressState.collectAsState()
    val usersData by progressViewModel.usersData.collectAsState()
    when(val state = uiState) {
        is ProgressUiState.Error -> ErrorProgressScreen { progressViewModel.prepareData() }
        ProgressUiState.Idle -> LoadingStub()
        ProgressUiState.Loading -> LoadingStub()
        is ProgressUiState.Success -> DefaultProgressScreen(
            onBackPage = onBackPage,
            usersData = usersData,
            progressList = state.progressHistory.first,
            historyList = state.progressHistory.second)
    }
}

@Suppress("DEPRECATION")
@Composable
fun DefaultProgressScreen(
    onBackPage: () -> Unit,
    usersData: UsersData?,
    progressList: List<UserProgress>,
    historyList: List<WorkoutHistory>
) {
    var visibleDatePicker by remember { mutableStateOf(false) }
    var currentDate by remember { mutableStateOf(LocalDate.now(ZoneId.of("Europe/Moscow"))) }
    val formatter = DateTimeFormatter.ofPattern("MMMM", Locale("en", "EN"))
    val monthNameFormatted = currentDate.format(formatter)
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
                        fontStyle = FontStyle.Italic,
                        color = colorResource(R.color.white),
                        modifier = Modifier.clickable(
                            onClick = { visibleDatePicker = !visibleDatePicker }
                        )
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Calendar(
                        currentDate = currentDate,
                        onDateSelected = { date ->
                            currentDate = date
                        }
                    )
                }
            }
        }
    }
    MonthPicker(
        visible = visibleDatePicker,
        currentMonth = LocalDate.now().month.value - 1,
        currentYear = LocalDate.now().year,
        confirmButtonClicked = { month, year ->
            currentDate = LocalDate.of(year, month, 1)
        },
        cancelClicked = {
            visibleDatePicker = !visibleDatePicker
        }
    )
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
fun ErrorProgressScreen(
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.bg_color)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.error_text),
                fontFamily = mulishFont(),
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.try_again),
                fontFamily = mulishFont(),
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    onClick = {
                        onRetryClick()
                    }
                )
            )
        }
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
                    Spacer(modifier = Modifier.width(4.dp)
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
                    Spacer(modifier = Modifier.width(4.dp)
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
        usersData?.photoUrl?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
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
}

@Preview
@Composable
fun PreviewUser() {
    UserDataScreen(UsersData(
        name = "Maddison",
        age = 18,
        weight = 75f,
        height = 1.65f,
        photoUrl = "url"))
}