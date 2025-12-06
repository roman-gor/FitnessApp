package com.gorman.fitnessapp.ui.screens.nutrition

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.AllergiesType
import com.gorman.fitnessapp.domain.models.CaloriesType
import com.gorman.fitnessapp.domain.models.DietType
import com.gorman.fitnessapp.ui.components.Header
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.components.RoundedButton
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.screens.workout.GenerationDefault
import com.gorman.fitnessapp.ui.states.GeneratingUiState
import com.gorman.fitnessapp.ui.viewmodel.GeneratingViewModel

@Composable
fun GeneratingMealScreen(
    onBackPage: () -> Unit,
    onNavigateToMenu: () -> Unit
) {
    val generatingViewModel: GeneratingViewModel = hiltViewModel()
    val uiState by generatingViewModel.genUiState.collectAsState()
    when (uiState) {
        is GeneratingUiState.Error -> {
            GenerationDefault(
                onClick = { generatingViewModel.onTryAgainClicked() },
                text = stringResource(R.string.meal_created_error),
                backgroundImage = R.drawable.meals_img,
                buttonText = stringResource(R.string.try_again)
            )
        }
        GeneratingUiState.Idle ->
            PreGenerationScreen(
                onBackPage = onBackPage,
                onStartGenerating = { diet, allergies, calories ->
                    generatingViewModel.generateMeals(
                        dietaryPreferences = diet.name,
                        exceptionProducts = allergies.map { it.name },
                        calories = calories.name
                    )
                }
            )
        GeneratingUiState.Loading -> LoadingStub()
        GeneratingUiState.MealsIsExist ->
            GenerationDefault(
                onClick = { generatingViewModel.resetUiState() },
                text = stringResource(R.string.meal_plan_exist),
                backgroundImage = R.drawable.meals_img,
                buttonText = stringResource(R.string.get_started)
            )
        GeneratingUiState.Success ->
            GenerationDefault(
                onClick = { onNavigateToMenu() },
                text = stringResource(R.string.meal_plan_success),
                backgroundImage = R.drawable.meals_img,
                buttonText = stringResource(R.string.to_home)
            )
        else ->
            PreGenerationScreen(
                onBackPage = onBackPage,
                onStartGenerating = { diet, allergies, calories ->
                    generatingViewModel.generateMeals(
                        dietaryPreferences = diet.name,
                        exceptionProducts = allergies.map { it.name },
                        calories = calories.name
                    )
                }
            )
    }
}

@Composable
fun PreGenerationScreen(
    onBackPage: () -> Unit,
    onStartGenerating: (diet: DietType, allergies: List<AllergiesType>, calories: CaloriesType) -> Unit
) {
    var dietaryPrefs by remember { mutableStateOf(DietType.NONE) }
    var allergies by remember { mutableStateOf(listOf<AllergiesType>()) }
    var calories by remember { mutableStateOf(CaloriesType.NONE) }
    Box(
        modifier = Modifier.fillMaxSize().systemBarsPadding()
            .background(colorResource(R.color.bg_color))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(
                onBackPage = onBackPage,
                text = stringResource(R.string.meal_plan)
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DietaryBlock(dietaryPrefs) { dietaryPrefs = it }
                Spacer(modifier = Modifier.height(16.dp))
                AllergiesBlock(allergies) { allergies = it }
                Spacer(modifier = Modifier.height(16.dp))
                CaloriesBlock(calories) { calories = it }
                RoundedButton(
                    onClick = { onStartGenerating(dietaryPrefs, allergies, calories) },
                    modifier = Modifier.width(150.dp).padding(top = 10.dp, bottom = 32.dp),
                    color = colorResource(R.color.meet_text),
                    textColor = colorResource(R.color.bg_color),
                    text = R.string.start
                )
            }
        }
    }
}

@Composable
fun DietaryBlock(
    selectedDiet: DietType,
    onDietSelected: (DietType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.dietary_prefs),
            fontFamily = mulishFont(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.meet_text),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.dietary_question),
            fontFamily = mulishFont(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        onClick = { onDietSelected(DietType.VEGETARIAN) },
                        selected = selectedDiet == DietType.VEGETARIAN,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.meet_text),
                            unselectedColor = colorResource(R.color.picker_wheel_bg)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.vegetarian),
                        fontFamily = mulishFont(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Start
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        onClick = { onDietSelected(DietType.VEGAN) },
                        selected = selectedDiet == DietType.VEGAN,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.meet_text),
                            unselectedColor = colorResource(R.color.picker_wheel_bg)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.vegan),
                        fontFamily = mulishFont(),
                        fontSize = 12.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Start
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        onClick = { onDietSelected(DietType.GLUTEN_FREE) },
                        selected = selectedDiet == DietType.GLUTEN_FREE,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.meet_text),
                            unselectedColor = colorResource(R.color.picker_wheel_bg)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.gluten_free),
                        fontFamily = mulishFont(),
                        fontSize = 12.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        onClick = { onDietSelected(DietType.KETO) },
                        selected = selectedDiet == DietType.KETO,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.meet_text),
                            unselectedColor = colorResource(R.color.picker_wheel_bg)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.keto),
                        fontFamily = mulishFont(),
                        fontSize = 12.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Start
                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        onClick = { onDietSelected(DietType.PALEO) },
                        selected = selectedDiet == DietType.PALEO,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.meet_text),
                            unselectedColor = colorResource(R.color.picker_wheel_bg)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.paleo),
                        fontFamily = mulishFont(),
                        fontSize = 12.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Start
                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        onClick = { onDietSelected(DietType.NOTHING) },
                        selected = selectedDiet == DietType.NOTHING,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.meet_text),
                            unselectedColor = colorResource(R.color.picker_wheel_bg)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.no_dietary_prefs),
                        fontFamily = mulishFont(),
                        fontSize = 12.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
fun AllergiesBlock(
    selectedAllergies: List<AllergiesType>,
    onAllergiesSelected: (List<AllergiesType>) -> Unit
) {
    fun toggle(item: AllergiesType) {
        val newList =
            if (selectedAllergies.contains(item))
                selectedAllergies - item
            else
                selectedAllergies + item
        onAllergiesSelected(newList)
    }
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.allergies),
            fontFamily = mulishFont(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.meet_text),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.allergies_question),
            fontFamily = mulishFont(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                AllergiesOption(
                    type = AllergiesType.DAIRY,
                    label = stringResource(R.string.dairy),
                    selected = selectedAllergies.contains(AllergiesType.DAIRY),
                    onToggle = ::toggle
                )
                AllergiesOption(
                    type = AllergiesType.SHELLFISH,
                    label = stringResource(R.string.shellfish),
                    selected = selectedAllergies.contains(AllergiesType.SHELLFISH),
                    onToggle = ::toggle
                )
                AllergiesOption(
                    type = AllergiesType.NUTS,
                    label = stringResource(R.string.nuts),
                    selected = selectedAllergies.contains(AllergiesType.NUTS),
                    onToggle = ::toggle
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                AllergiesOption(
                    type = AllergiesType.EGGS,
                    label = stringResource(R.string.eggs),
                    selected = selectedAllergies.contains(AllergiesType.EGGS),
                    onToggle = ::toggle
                )
                AllergiesOption(
                    type = AllergiesType.NOTHING,
                    label = stringResource(R.string.no_allergies),
                    selected = selectedAllergies.contains(AllergiesType.NOTHING),
                    onToggle = ::toggle
                )
            }
        }
    }
}

@Composable
private fun AllergiesOption(
    type: AllergiesType,
    label: String,
    selected: Boolean,
    onToggle: (AllergiesType) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            onClick = { onToggle(type) },
            selected = selected,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(R.color.meet_text),
                unselectedColor = colorResource(R.color.picker_wheel_bg)
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontFamily = mulishFont(),
            fontSize = 12.sp,
            maxLines = 2,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.white)
        )
    }
}

@Composable
fun CaloriesBlock(
    selectedCalories: CaloriesType,
    onCaloriesSelected: (CaloriesType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calories),
            fontFamily = mulishFont(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.meet_text),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.caloric_question),
            fontFamily = mulishFont(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    onClick = { onCaloriesSelected(CaloriesType.LESS_1500) },
                    selected = selectedCalories == CaloriesType.LESS_1500,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.meet_text),
                        unselectedColor = colorResource(R.color.picker_wheel_bg)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.less_than_1500),
                    fontFamily = mulishFont(),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Start
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    onClick = { onCaloriesSelected(CaloriesType.FROM_1500_TO_2000) },
                    selected = selectedCalories == CaloriesType.FROM_1500_TO_2000,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.meet_text),
                        unselectedColor = colorResource(R.color.picker_wheel_bg)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.from_1500_to_2000),
                    fontFamily = mulishFont(),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Start
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    onClick = { onCaloriesSelected(CaloriesType.MORE_2000) },
                    selected = selectedCalories == CaloriesType.MORE_2000,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.meet_text),
                        unselectedColor = colorResource(R.color.picker_wheel_bg)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.more_than_2000),
                    fontFamily = mulishFont(),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Start
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    onClick = { onCaloriesSelected(CaloriesType.NO_MATTER) },
                    selected = selectedCalories == CaloriesType.NO_MATTER,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.meet_text),
                        unselectedColor = colorResource(R.color.picker_wheel_bg)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.no_calories_prefs),
                    fontFamily = mulishFont(),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}