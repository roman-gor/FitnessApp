package com.gorman.fitnessapp.ui.screens.setup

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.gorman.fitnessapp.ui.components.SetupBackButton
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun GenderScreen(onNextPage: (UsersData) -> Unit,
                 onBackPage: () -> Unit) {
    var isMale by remember { mutableStateOf(true) }
    val malePainterResource =
        if (isMale) painterResource(R.drawable.male_on) else
            painterResource(R.drawable.male_off)
    val femalePainterResource =
        if (isMale) painterResource(R.drawable.fmale_off) else
            painterResource(R.drawable.fmale_on)

    Column (
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.bg_color)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(start = 32.dp, top = 48.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            SetupBackButton { onBackPage() }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = stringResource(R.string.choose_gender),
            fontFamily = mulishFont(),
            fontSize = 30.sp,
            lineHeight = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = femalePainterResource,
                contentDescription = "Female Gender",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .clickable {isMale = false},
                contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.height(10.dp))
            Image(painter = malePainterResource,
                contentDescription = "Male Gender",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .clickable {isMale = true},
                contentScale = ContentScale.Crop)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SetupNextButton(
                onClick = {
                    onNextPage(
                        UsersData(
                            gender = if (isMale) "M" else "F"
                        )
                    ) },
                textColor = Color.White,
                containerColor = Color.White.copy(alpha = 0.1f),
                text = stringResource(R.string.continue_string)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenderPreview() {
    MaterialTheme {
        GenderScreen(onBackPage = {}, onNextPage = {})
    }
}