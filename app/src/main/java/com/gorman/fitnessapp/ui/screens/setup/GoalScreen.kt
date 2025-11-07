package com.gorman.fitnessapp.ui.screens.setup

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.GoalItem
import com.gorman.fitnessapp.ui.components.SetupBackButton
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun GoalScreen(
    usersData: UsersData? = null,
    onBackPage: () -> Unit,
    onNextPage: (UsersData) -> Unit
) {
    var goal by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.bg_color)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 32.dp, top = 48.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SetupBackButton { onBackPage() }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.choose_goal),
            fontFamily = mulishFont(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(colorResource(R.color.picker_wheel_bg)),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 40.dp)
            ) {
                itemsIndexed(listOf(
                    R.string.lose_weight,
                    R.string.gain_weight,
                    R.string.muscle_muss_gain,
                    R.string.shape_body,
                    R.string.for_soul
                )) { index, item ->
                    val goalName = stringResource(item)
                    GoalItem(
                        goalName = goalName,
                        onClick = {
                            goal = it
                        },
                        isSelected = goal == goalName
                    )
                    if (index != 4)
                        Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SetupNextButton(
                onClick = {
                    usersData?.let {
                        onNextPage(
                            it.copy(goal = goal)
                        )
                    }},
                text = stringResource(R.string.continue_string)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun GoalScreenPreview() {
    MaterialTheme {
        GoalScreen(onBackPage = {}, onNextPage = {})
    }
}
