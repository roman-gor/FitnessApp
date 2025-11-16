package com.gorman.fitnessapp.ui.screens.setup

import android.util.Patterns
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.components.SetupBackButton
import com.gorman.fitnessapp.ui.components.SetupNextButton
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.states.RegisterUiState
import com.gorman.fitnessapp.ui.viewmodel.RegisterViewModel

@Composable
fun ProfileSetupScreen(
    usersData: UsersData? = null,
    onBackPage: () -> Unit,
    onNextPage: () -> Unit
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val uiState by registerViewModel.registerUiState.collectAsState()
    DefaultProfileScreen(
        uiState = uiState,
        onBackPage = onBackPage,
        usersData = usersData,
        registerViewModel = registerViewModel
    )
    when(val state = uiState){
        is RegisterUiState.Loading -> {
            LoadingStub()
        }
        is RegisterUiState.Success -> {
            onNextPage()
        }
        is RegisterUiState.Error -> {
            ErrorMessage(
                message = state.message,
                onDismiss = {
                    registerViewModel.resetState()
                }
            )
        }
        else -> {}
    }
}

@Composable
fun DefaultProfileScreen(
    uiState: RegisterUiState,
    onBackPage: () -> Unit,
    usersData: UsersData?,
    registerViewModel: RegisterViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isDataError by remember { mutableStateOf(false) }
    val placeholderTextColor =
        if (!isDataError)
            colorResource(R.color.bg_color).copy(alpha = 0.3f)
        else
            Color.Red.copy(alpha = 0.3f)
    var uriImage by remember { mutableStateOf("") }
    var uriPath by remember { mutableStateOf<Uri?>(null) }
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                uriPath = uri
                uriImage = uri.toString()
            }
        }
    )
    Column (
        modifier = if (uiState != RegisterUiState.Loading)
            Modifier.fillMaxSize()
                .background(colorResource(R.color.bg_color))
        else
            Modifier.fillMaxSize()
                .background(colorResource(R.color.bg_color))
                .blur(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            if (uiState is RegisterUiState.Error) Arrangement.Bottom
            else Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 32.dp, top = 48.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SetupBackButton { onBackPage() }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.fill_profile),
            fontFamily = mulishFont(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.picker_wheel_bg)),
            contentAlignment = Alignment.Center) {
            Box (modifier = Modifier.size(160.dp).padding(16.dp)){
                AsyncImage(
                    model = if (uriImage.isNotBlank())
                        ImageRequest.Builder(LocalContext.current)
                            .data(uriImage)
                            .crossfade(true)
                            .build()
                    else null,
                    contentDescription = "Firebase Image",
                    placeholder = painterResource(R.drawable.placeholder_ava),
                    error = painterResource(R.drawable.placeholder_ava),
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop)
                Image(painter = painterResource(R.drawable.edit_ava),
                    contentDescription = "Edit Avatar",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(36.dp)
                        .clickable(onClick = {
                            pickImage.launch("image/*")
                        }, indication = null, interactionSource = null))
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            ){
                Text(
                    text = stringResource(R.string.name),
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.font_purple_color),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isDataError = false },
                    placeholder = { Text(
                        text = stringResource(R.string.enter_name),
                        fontFamily = mulishFont(),
                        fontWeight = FontWeight.Medium,
                        color = placeholderTextColor,
                        fontSize = 18.sp
                    )},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = mulishFont(),
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.bg_color),
                        fontSize = 18.sp
                    ),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            ){
                Text(
                    text = stringResource(R.string.email),
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.font_purple_color),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isDataError = false },
                    placeholder = { Text(
                        text = stringResource(R.string.enter_email),
                        fontFamily = mulishFont(),
                        fontWeight = FontWeight.Medium,
                        color = placeholderTextColor,
                        fontSize = 18.sp
                    )},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = mulishFont(),
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.bg_color),
                        fontSize = 18.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
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
                            if (name.isNotBlank() && email.isNotBlank())
                                if (isValidEmail(email))
                                    registerViewModel.registerUser(
                                        uri = uriPath,
                                        usersData = it.copy(
                                            name = name,
                                            email = email
                                        ))
                            else
                                isDataError = true
                        }
                    },
                    textColor = colorResource(R.color.bg_color),
                    containerColor = colorResource(R.color.meet_text),
                    text = stringResource(R.string.start)
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                onClick = { onDismiss() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.bg_color)
            ),
            border = BorderStroke(4.dp, colorResource(R.color.meet_text)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = mulishFont(),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    MaterialTheme {
        //ProfileSetupScreen(onBackPage = {}, onNextPage = {})
        //LoadingStub()
    }
}