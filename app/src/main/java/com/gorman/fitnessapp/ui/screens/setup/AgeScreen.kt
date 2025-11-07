package com.gorman.fitnessapp.ui.screens.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.gorman.fitnessapp.ui.components.AgePickerWheel
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.components.WeightPickerWheel
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun AgeScreen(
    usersData: UsersData? = null,
    onNextPage: (UsersData) -> Unit,
    onBackPage: () -> Unit) {
    var age by remember { mutableLongStateOf(0) }
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
            Row (
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = {onBackPage()}),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Arrow Back",
                    tint = colorResource(R.color.meet_text),
                    modifier = Modifier.height(40.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.back),
                    fontFamily = mulishFont(),
                    fontSize = 18.sp,
                    color = colorResource(R.color.meet_text),
                    modifier = Modifier.padding(2.dp))
            }
        }
        Text(text = stringResource(R.string.choose_age),
            fontFamily = mulishFont(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.weight(1f))
        AgePickerWheel(
            initialAge = 18,
            ageRange = IntRange(14,100)
        ) {
            age = it
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
                            it.copy(age = age)
                        )
                    }},
                text = stringResource(R.string.continue_string)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgePreview() {
    MaterialTheme {
        AgeScreen(onNextPage = {}, onBackPage = {})
    }
}