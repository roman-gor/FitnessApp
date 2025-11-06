package com.gorman.fitnessapp.ui.screens.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.viewmodel.RegisterViewModel

@Composable
fun WelcomeScreen(onNextPage: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize().
        background(colorResource(R.color.bg_color))
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1.2f)){
            Image(
                painter = painterResource(R.drawable.training_process_background),
                contentDescription = "BackgroundImage",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .weight(0.6f)
                .padding(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.meet_text),
                textAlign = TextAlign.Center,
                fontFamily = mulishFont(),
                fontSize = 26.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.meet_text)
            )
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SetupNextButton(
                onClick = {onNextPage()},
                text = stringResource(R.string.next)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MaterialTheme {
        WelcomeScreen {  }
    }
}
