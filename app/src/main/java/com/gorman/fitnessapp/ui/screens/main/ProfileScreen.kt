package com.gorman.fitnessapp.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.DefaultOutlinedTextField
import com.gorman.fitnessapp.ui.components.GeneralBackButton
import com.gorman.fitnessapp.ui.components.RoundedButton
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.navigation.Screen
import com.gorman.fitnessapp.ui.navigation.Screen.Companion.pItems
import com.gorman.fitnessapp.ui.states.RegisterUiState
import com.gorman.fitnessapp.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userData: UsersData,
    onBackPage: () -> Unit,
    onSettingsClick: (UsersData) -> Unit,
    onNavigateToStart: () -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        profileViewModel.uiEvent.collect {
            when(it) {
                is RegisterUiState.Logout -> onNavigateToStart()
                else -> {}
            }
        }
    }
    val editSheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to colorResource(R.color.picker_wheel_bg),
                        0.45f to colorResource(R.color.picker_wheel_bg),
                        0.45f to colorResource(R.color.bg_color),
                        1.0f to colorResource(R.color.bg_color)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 28.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                GeneralBackButton(
                    onClick = { onBackPage() },
                    text = stringResource(R.string.profile_title),
                    textColor = colorResource(R.color.white)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box (modifier = Modifier
                .size(140.dp)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userData.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Firebase Image",
                    placeholder = painterResource(R.drawable.placeholder_ava),
                    error = painterResource(R.drawable.placeholder_ava),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(140.dp),
                    contentScale = ContentScale.Crop)
            }
            userData.name?.let {
                Text(
                    text = it,
                    fontFamily = mulishFont(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(8.dp)
                )
            }
            InformationCard(userData)
            Spacer(modifier = Modifier.height(16.dp))
            for(item in pItems) {
                ProfileItem(item, onItemClick = { name->
                    when(name) {
                        R.string.edit -> {
                            showSheet = !showSheet
                        }
                        R.string.settings -> {
                            onSettingsClick(userData)
                        }
                        R.string.logout -> {
                            profileViewModel.logoutFromDevice()
                        }
                    }
                })
            }
        }
        if(showSheet) {
            BottomSheetDialog(
                onDismiss = { showSheet = !showSheet },
                sheetState = editSheetState,
                user = userData,
                onSave = { newUserData ->
                    profileViewModel.updateUserData(newUserData)
                })
        }
    }
}

@Composable
fun InformationCard(userData: UsersData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.font_purple_color)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${userData.weight?.toInt()} Kg",
                    fontFamily = mulishFont(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)
                )
                Text(
                    text = stringResource(R.string.weight),
                    fontFamily = mulishFont(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.white)
                )
            }
            Spacer(modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(0.7f)
                .background(colorResource(R.color.white)))
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${userData.age?.toInt()}",
                    fontFamily = mulishFont(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)
                )
                userData.age?.let {
                    Text(
                        text = ageSuffix(it.toInt()),
                        fontFamily = mulishFont(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white)
                    )
                }
            }
            Spacer(modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(0.7f)
                .background(colorResource(R.color.white)))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                userData.height?.let {
                    Text(
                        text = "${heightToMeters(it)} M",
                        fontFamily = mulishFont(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white)
                    )
                }
                Text(
                    text = stringResource(R.string.height),
                    fontFamily = mulishFont(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.white)
                )
            }
        }
    }
}

@Composable
fun ProfileItem(
    item: Screen.ProfileItemsScreen,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(
                onClick = {
                    onItemClick(item.title)
                }
            )
            .padding(horizontal = 32.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(item.icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(item.title),
                fontFamily = mulishFont(),
                fontSize = 16.sp,
                color = colorResource(R.color.white),
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = null,
            modifier = Modifier.rotate(180f),
            tint = colorResource(R.color.meet_text)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(onDismiss: () -> Unit,
                      sheetState: SheetState,
                      user: UsersData,
                      onSave: (UsersData) -> Unit) {
    var newUsername by remember { mutableStateOf("") }
    var newAge by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var newWeight by remember { mutableStateOf("") }
    var newDesiredWeight by remember { mutableStateOf("") }
    user.name?.let {
        newUsername = it
    }
    user.age?.let {
        newAge = it.toString()
    }
    user.email?.let {
        newEmail = it
    }
    user.weight?.let {
        newWeight = String.format("%.0f", it)
    }
    user.desiredWeight?.let {
        newDesiredWeight = String.format("%.0f", it)
    }
    ModalBottomSheet(
        onDismissRequest = {onDismiss()},
        sheetState = sheetState,
        containerColor = colorResource(R.color.bg_color)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column (
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ){
                Text(text = stringResource(R.string.enter_name),
                    fontSize = 12.sp,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp))
                DefaultOutlinedTextField(
                    value = newUsername,
                    onValueChange = {
                        newUsername = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth())
            }
            Column (
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ){
                Text(text = stringResource(R.string.enter_email),
                    fontSize = 12.sp,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp))
                DefaultOutlinedTextField(
                    value = newEmail,
                    onValueChange = {
                        newEmail = it
                    },
                    placeholder = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth())
            }
            Column (
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ){
                Text(text = stringResource(R.string.enter_age),
                    fontSize = 12.sp,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp))
                DefaultOutlinedTextField(
                    value = newAge,
                    onValueChange = {
                        newAge = it
                    },
                    placeholder = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth())
            }
            Column (
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ){
                Text(text = stringResource(R.string.enter_weight),
                    fontSize = 12.sp,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp))
                DefaultOutlinedTextField(
                    value = newWeight,
                    onValueChange = {
                        newWeight = it
                    },
                    placeholder = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth())
            }
            Column (
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ){
                Text(text = stringResource(R.string.enter_desired_weight),
                    fontSize = 12.sp,
                    fontFamily = mulishFont(),
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp))
                DefaultOutlinedTextField(
                    value = newDesiredWeight,
                    onValueChange = {
                        newDesiredWeight = it
                    },
                    placeholder = null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth())
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ){
                RoundedButton(
                    onClick = {
                        onDismiss() },
                    modifier = Modifier.weight(1f),
                    color = colorResource(R.color.white),
                    text = R.string.cancel,
                    textColor = colorResource(R.color.font_purple_color))
                RoundedButton(onClick = {
                    val newUser = user.copy(
                        name = newUsername,
                        email = newEmail,
                        age = newAge.toLong(),
                        weight = newWeight.toFloat(),
                        desiredWeight = newDesiredWeight.toFloat()
                    )
                    onDismiss()
                    onSave(newUser) },
                    modifier = Modifier.weight(1f),
                    color = colorResource(R.color.meet_text),
                    text = R.string.save,
                    textColor = colorResource(R.color.bg_color))
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun heightToMeters(height: Float): String {
    return String.format("%.2f", height/100)
}

@Composable
fun ageSuffix(age: Int): String {
    val lastTwoDigits = age % 100
    val lastDigit = age % 10

    return when {
        lastTwoDigits in 11..14 -> stringResource(R.string.years_old)
        lastDigit == 1 -> stringResource(R.string.years_old1)
        lastDigit in 2..4 -> stringResource(R.string.years_old2)
        else -> stringResource(R.string.years_old)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {

    }
}