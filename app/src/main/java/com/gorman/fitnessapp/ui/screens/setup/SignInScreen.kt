package com.gorman.fitnessapp.ui.screens.setup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.components.DefaultOutlinedTextField
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.RegisterUiState
import com.gorman.fitnessapp.ui.viewmodel.RegisterViewModel

@Composable
fun SignInScreen(
    onSignUpScreen: () -> Unit,
    onHomePage: () -> Unit
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val uiState by registerViewModel.registerUiState
    SignInDefault(
        onSignInScreen = { email->
            registerViewModel.signInUser(email)
        },
        onSignUpScreen = { onSignUpScreen() }
    )
    when(val state = uiState) {
        is RegisterUiState.Error -> {
            ErrorMessage(
                message = state.message,
                onDismiss = {
                    registerViewModel.resetState()
                }
            )
        }
        RegisterUiState.Loading -> {
            LoadingStub()
        }
        RegisterUiState.Success -> {
            onHomePage()
            registerViewModel.resetState()
        }
        else -> {}
    }

}

@Suppress("DEPRECATION")
@Composable
fun SignInDefault(
    onSignInScreen: (String) -> Unit,
    onSignUpScreen: () -> Unit
) {
    val offer = stringResource(R.string.offer_to_register)
    val signUp = stringResource(R.string.sign_up)
    var email by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.bg_color)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.log_in),
                fontFamily = mulishFont(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.meet_text),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 32.dp))
            Spacer(modifier = Modifier.height(250.dp))
            Column (
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.enter_email),
                    fontSize = 12.sp,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp)
                )
                DefaultOutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth())
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onSignInScreen(email)
                },
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
                Text(text = stringResource(R.string.log_in),
                    fontFamily = mulishFont(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(R.color.white),
                    modifier = Modifier
                        .padding(8.dp))
            }
        }
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White.copy(alpha = 0.8f))) {
                    append("$offer ")
                }
                pushStringAnnotation(
                    tag = "SIGN_UP_TAG",
                    annotation = "SIGN_UP_CLICK"
                )
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(signUp)
                }
                pop()
            },
            onClick = { offset ->
                val annotations = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White.copy(alpha = 0.8f))) {
                        append("$offer ")
                    }
                    pushStringAnnotation(
                        tag = "SIGN_UP_TAG",
                        annotation = "SIGN_UP_CLICK"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(signUp)
                    }
                    pop()
                }.getStringAnnotations(
                    tag = "SIGN_UP_TAG",
                    start = offset,
                    end = offset
                )

                if (annotations.isNotEmpty()) {
                    println("Нажата ссылка: ${annotations.first().item}")
                    onSignUpScreen()
                }
            },
            style = TextStyle(
                fontFamily = mulishFont(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignIn() {

}