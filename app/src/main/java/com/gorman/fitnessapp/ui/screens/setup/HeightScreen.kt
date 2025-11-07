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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.HeightPickerWheel
import com.gorman.fitnessapp.ui.components.SetupBackButton
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun HeightScreen(
    usersData: UsersData? = null,
    onNextPage: (UsersData) -> Unit,
    onBackPage: () -> Unit
) {
    var height by remember { mutableFloatStateOf(160f) }
    val heightStringUnit = stringResource(R.string.cm)
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
            text = stringResource(R.string.choose_height),
            fontFamily = mulishFont(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row (
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                text = heightStringUnit,
                fontFamily = mulishFont(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.Transparent,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = height.toInt().toString(),
                fontFamily = mulishFont(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 60.sp,
                color = Color.White,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = heightStringUnit,
                fontFamily = mulishFont(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.alignByBaseline()
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HeightPickerWheel(
                initialValue = 160,
                range = IntRange(100,250)
            ) {
                height = it
            }
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = "Arrow Back",
                tint = colorResource(R.color.meet_text),
                modifier = Modifier.scale(1.3f)
            )
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
                            it.copy(height = height)
                        )
                    }},
                text = stringResource(R.string.continue_string)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeightScreenPreview() {
    MaterialTheme {
        HeightScreen(onNextPage = {}, onBackPage = {})
    }
}