package com.gorman.fitnessapp.ui.screens.workout

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.GeneratingUiState
import com.gorman.fitnessapp.ui.viewmodel.GeneratingViewModel

@Composable
fun GeneratingProgram(
    onNavigateToProgram: () -> Unit
) {
    val context = LocalContext.current
    val generatingViewModel: GeneratingViewModel = hiltViewModel()
    val uiState by generatingViewModel.genUiState.collectAsState()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    when (val state = uiState) {
        GeneratingUiState.IsExist -> GenerationDefault (
            onClick = { generatingViewModel.generateProgram() },
            text = stringResource(R.string.confirm_to_gen_program),
            buttonText = stringResource(R.string.get_started))
        is GeneratingUiState.Error -> {
            val errorString = stringResource(R.string.program_created_error)
            LaunchedEffect(Unit) {
                Toast.makeText(context, "$errorString\n${state.message}", Toast.LENGTH_SHORT).show()
            }
            GenerationDefault (
                onClick = { generatingViewModel.generateProgram() },
                text = stringResource(R.string.program_created_error),
                buttonText = stringResource(R.string.try_again))
        }
        GeneratingUiState.Idle -> GenerationDefault (
            onClick = { generatingViewModel.generateProgram() },
            text = stringResource(R.string.program_gen_description_text),
            buttonText = stringResource(R.string.get_started))
        GeneratingUiState.Loading -> {
            val loadingString = stringResource(R.string.generation_loading)
            LaunchedEffect(Unit) {
                Toast.makeText(context, loadingString, Toast.LENGTH_LONG).show()
            }
            LoadingGeneration(composition, progress)
        }
        GeneratingUiState.Success -> {
            val successString = stringResource(R.string.program_created_success)
            LaunchedEffect(Unit) {
                Toast.makeText(context, successString, Toast.LENGTH_SHORT).show()
            }
            GenerationDefault (
                onClick = { onNavigateToProgram() },
                text = stringResource(R.string.program_created_success),
                buttonText = stringResource(R.string.get_program_screen))
        }
    }
}

@Composable
fun GenerationDefault(
    onClick: () -> Unit,
    text: String,
    buttonText: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.start_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
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
                onClick = { onClick() },
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
                Text(text = buttonText,
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

@Composable
fun LoadingGeneration(
    composition: LottieComposition?,
    progress: Float) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.start_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)))
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(160.dp)
        )
    }
}