package com.gorman.fitnessapp.ui.screens.nutrition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.ui.components.Header
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.NutritionUiState
import com.gorman.fitnessapp.ui.viewmodel.NutritionViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun NutritionScreen(
    onBackPage: () -> Unit
) {
    val nutritionViewModel: NutritionViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        nutritionViewModel.prepareData()
        nutritionViewModel.getMeals()
    }
    val uiState by nutritionViewModel.nutritionUiState.collectAsState()
    val meals by nutritionViewModel.mealsState.collectAsState()
    when(val state = uiState) {
        is NutritionUiState.Error -> ErrorNutritionScreen { nutritionViewModel.prepareData() }
        NutritionUiState.Idle -> Box(Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_color)))
        NutritionUiState.Loading -> LoadingStub()
        is NutritionUiState.Success ->
            DefaultNutritionScreen(
                onBackPage = onBackPage,
                meals = meals,
                mealPlanTemplate = state.mealPlan.first,
                mealPlanItems = state.mealPlan.second
            )
    }
}

@Composable
fun DefaultNutritionScreen(
    onBackPage: () -> Unit,
    meals: List<Meal>,
    mealPlanTemplate: MealPlanTemplate,
    mealPlanItems: List<MealPlanItem>
) {
    val itemsByDayAndType: Map<DayOfWeek, Map<String, List<MealPlanItem>>> =
        mealPlanItems.groupBy {
            Instant.ofEpochMilli(it.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .dayOfWeek
        }.mapValues { (_, items) ->
            items.groupBy { it.mealType }
        }

    val pages: List<Pair<DayOfWeek, Map<String, Meal>>> =
        DayOfWeek.entries.map { dayOfWeek ->
            dayOfWeek to itemsByDayAndType[dayOfWeek].orEmpty()
                .mapValues { (_, planItems) ->
                    meals.find { meal -> meal.localId == planItems.first().mealId }
                }
                .filterValues { it != null }
                .mapValues { it.value!! }
        }
    val pagerState = rememberPagerState(initialPage = LocalDate.now().dayOfWeek.value - 1, pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.bg_color))) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.bg_color))
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(
                onBackPage = { onBackPage() },
                text = stringResource(R.string.nutrition)
            )
            Text(
                text = mealPlanTemplate.name,
                fontFamily = mulishFont(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                textAlign = TextAlign.Start,
                maxLines = 2,
                lineHeight = 35.sp,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(R.color.meet_text),
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 20.dp)
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(R.color.white)))
            Spacer(modifier = Modifier.height(20.dp))
            MealsByDayPager(
                pages = pages,
                pagerState = pagerState,
                onNextClick = {
                    scope.launch {
                        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(nextPage)
                    }
                },
                onPreviousClick = {
                    scope.launch {
                        val previousPage = pagerState.currentPage - 1
                        pagerState.animateScrollToPage(previousPage)
                    }
                }
            )
        }
    }
}

@Composable
fun MealsByDayPager(
    pages: List<Pair<DayOfWeek, Map<String, Meal>>>,
    pagerState: PagerState,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false) { pageIndex ->
        val (day, mealsForDay) = pages[pageIndex]
        MealPlanByDayPage(
            index = pageIndex,
            day = day,
            meal = mealsForDay,
            onNextClick = onNextClick,
            onPreviousClick = onPreviousClick
        )
    }
}

@Composable
fun MealPlanByDayPage(
    index: Int,
    day: DayOfWeek,
    meal: Map<String, Meal>,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    val iconColor =
        if (index != 0) colorResource(R.color.white)
        else colorResource(R.color.white).copy(alpha = 0.3f)
    val nextIconColor =
        if (index < DayOfWeek.entries.size - 1) colorResource(R.color.white)
        else colorResource(R.color.white).copy(alpha = 0.3f)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(getStringDate(day)),
                fontFamily = mulishFont(),
                fontSize = 22.sp,
                color = colorResource(R.color.meet_text),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.arrow_nutrition),
                    contentDescription = "Previous Day",
                    tint = iconColor,
                    modifier = Modifier
                        .rotate(180f)
                        .size(16.dp)
                        .clickable(
                            enabled = index != 0,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onPreviousClick
                        )
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    painter = painterResource(R.drawable.arrow_nutrition),
                    contentDescription = "Next Day",
                    tint = nextIconColor,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(
                            enabled = index < DayOfWeek.entries.size - 1,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onNextClick
                        )
                )
            }
        }

        val sortedMeals = meal.entries.sortedBy { getMealOrder(it.key) }

        sortedMeals.forEach { (type, item) ->
            val typeText = when (type) {
                "Завтрак" -> stringResource(R.string.breakfast)
                "Обед" -> stringResource(R.string.lunch)
                "Ужин" -> stringResource(R.string.dinner)
                "Перекус" -> stringResource(R.string.snack)
                else -> type
            }
            Text(
                text = typeText,
                fontFamily = mulishFont(),
                fontSize = 16.sp,
                color = colorResource(R.color.meet_text),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                textAlign = TextAlign.Start
            )
            MealItem(item) {  }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

private fun getMealOrder(mealType: String): Int {
    return when (mealType) {
        "Завтрак" -> 1
        "Перекус" -> 2
        "Обед" -> 3
        "Ужин" -> 4
        else -> 5
    }
}

@Composable
fun MealItem(
    meal: Meal,
    onMealClick: (meal: Meal) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .padding(vertical = 4.dp)
            .clickable(onClick = {
                onMealClick(meal)
            }),
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
                modifier = Modifier
                    .height(125.dp)
                    .weight(1f)
                    .padding(start = 16.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = meal.name,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.bg_color),
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.fire_icon),
                        tint = colorResource(R.color.bg_color),
                        contentDescription = null,
                        modifier = Modifier.scale(1.4f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${meal.calories.toInt()} ${stringResource(R.string.calories)}",
                        fontFamily = mulishFont(),
                        color = colorResource(R.color.bg_color),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${stringResource(R.string.pfc)}: " +
                            "${meal.protein.toInt()}/${meal.fats.toInt()}/${meal.carbs.toInt()}",
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.bg_color),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(meal.photo)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.articles_placeholder),
                error = painterResource(R.drawable.articles_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .height(145.dp)
                    .width(155.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun getStringDate(day: DayOfWeek): Int {
    return when(day) {
        DayOfWeek.FRIDAY -> R.string.friday
        DayOfWeek.MONDAY -> R.string.monday
        DayOfWeek.SATURDAY -> R.string.saturday
        DayOfWeek.SUNDAY -> R.string.sunday
        DayOfWeek.THURSDAY -> R.string.thursday
        DayOfWeek.TUESDAY -> R.string.tuesday
        DayOfWeek.WEDNESDAY -> R.string.wednesday
    }
}

@Composable
fun ErrorNutritionScreen(
    onRetryClick: () -> Unit
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
                text = stringResource(R.string.meal_plan_error),
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
                onClick = { onRetryClick() },
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
                Text(text = stringResource(R.string.try_again),
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