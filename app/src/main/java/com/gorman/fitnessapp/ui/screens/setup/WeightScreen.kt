package com.gorman.fitnessapp.ui.screens.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.DualButton
import com.gorman.fitnessapp.ui.components.SetupBackButton
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.components.WeightPickerWheel
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun WeightScreen(
    usersData: UsersData? = null,
    onNextPage: (UsersData) -> Unit,
    onBackPage: () -> Unit
) {
    var weight by remember { mutableFloatStateOf(0f) }
    var weightUnit by remember { mutableStateOf("kg") }
    val weightRange =
        if (weightUnit == "kg") IntRange(10,200)
        else IntRange(10,400)
    val weightStringUnit =
        if (weightUnit == "kg") stringResource(R.string.kg)
        else stringResource(R.string.lb)
    Column (
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.bg_color)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(start = 32.dp, top = 48.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            SetupBackButton { onBackPage() }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = stringResource(R.string.choose_weight),
            fontFamily = mulishFont(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            lineHeight = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.weight(1f))
        DualButton(
            onLeftClick = {
                weightUnit = "kg"
            },
            onRightClick = {
                weightUnit = "lb"
            },
            leftTag = stringResource(R.string.kg),
            rightTag = stringResource(R.string.lb),
            containerColor = colorResource(R.color.meet_text),
            textColor = colorResource(R.color.bg_color),
            dividerColor = colorResource(R.color.bg_color)
        )
        Spacer(modifier = Modifier.weight(1f))
        WeightPickerWheel(
            initialValue = 50,
            range = weightRange
        ) {
            weight = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(painter = painterResource(R.drawable.arrow_up),
                contentDescription = "Arrow Up",
                tint = colorResource(R.color.meet_text),
                modifier = Modifier.width(46.dp))
            Spacer(modifier = Modifier.height(14.dp))
            Row (
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = weightStringUnit,
                    fontFamily = mulishFont(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.Transparent,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = weight.toInt().toString(),
                    fontFamily = mulishFont(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 60.sp,
                    color = Color.White,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = weightStringUnit,
                    fontFamily = mulishFont(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.alignByBaseline()
                )
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
                        if (weightUnit == "lb")
                            weight = weight / 2.205f
                        onNextPage(
                            it.copy(weight = weight)
                        )
                    }},
                textColor = Color.White,
                containerColor = Color.White.copy(alpha = 0.1f),
                text = stringResource(R.string.continue_string)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeightPreview() {
    MaterialTheme {
        WeightScreen(onBackPage = {}, onNextPage = {})
    }
}