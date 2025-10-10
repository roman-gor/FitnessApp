package com.gorman.fitnessapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterScreen() {

    val registerViewModel: RegisterViewModel = hiltViewModel()
    val context = LocalContext.current
    //Necessary variables
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var weightString by remember { mutableStateOf("0.0") }
    var weightValue by remember { mutableFloatStateOf(0.0f) }
    var desiredWeightString by remember { mutableStateOf("0.0") }
    var desiredWeightValue by remember { mutableFloatStateOf(0.0f) }
    var heightString by remember { mutableStateOf("0.0") }
    var heightValue by remember { mutableFloatStateOf(0.0f) }
    var gender by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    var date: Date? = null
    var expandedGoalMenu by remember { mutableStateOf(false) }
    var expandedActivityLevelMenu by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Create new user", modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            placeholder = {Text("Name")}
        )
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            placeholder = {Text("Email")}
        )
        OutlinedTextField(
            value = birthday,
            onValueChange = {birthday = it},
            placeholder = {Text("Birthday")}
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expandedGoalMenu = true })
                .then(Modifier.wrapContentSize(Alignment.TopStart))
        ) {
            Text(
                text = goal,
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expandedGoalMenu,
                onDismissRequest = { expandedGoalMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Lose weight") },
                    onClick = {
                        goal = "Lose weight"
                        expandedGoalMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Gain weight") },
                    onClick = {
                        goal = "Gain weight"
                        expandedGoalMenu = false
                    }
                )
            }
        }
        OutlinedTextField(
            value = weightString,
            onValueChange = { newValue ->
                weightString = newValue
                val newFloat = newValue.toFloatOrNull()
                if (newFloat != null) {
                    weightValue = newFloat
                }
            },
            placeholder = {Text("Weight")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = desiredWeightString,
            onValueChange = { newValue ->
                desiredWeightString = newValue
                val newFloat = newValue.toFloatOrNull()
                if (newFloat != null) {
                    desiredWeightValue = newFloat
                }
            },
            placeholder = {Text("Desired Weight")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = heightString,
            onValueChange = { newValue ->
                heightString = newValue
                val newFloat = newValue.toFloatOrNull()
                if (newFloat != null) {
                    heightValue = newFloat
                }
            },
            placeholder = {Text("Height")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = gender,
            onValueChange = {gender = it},
            placeholder = {Text("Gender")}
        )
        OutlinedTextField(
            value = photoUrl,
            onValueChange = {photoUrl = it},
            placeholder = {Text("Photo URL")}
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expandedActivityLevelMenu = true })
                .then(Modifier.wrapContentSize(Alignment.TopStart))
        ) {
            Text(
                text = goal,
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(expanded = expandedActivityLevelMenu,
                onDismissRequest = {expandedActivityLevelMenu = false}) {
                DropdownMenuItem(
                    text = { Text("Beginner") },
                    onClick = {activityLevel = "Beginner"; expandedActivityLevelMenu = false}
                )
                DropdownMenuItem(
                    text = { Text("Medium") },
                    onClick = {activityLevel = "Medium"; expandedActivityLevelMenu = false}
                )
                DropdownMenuItem(
                    text = { Text("Strong") },
                    onClick = {activityLevel = "Strong"; expandedActivityLevelMenu = false}
                )
            }
        }
        OutlinedTextField(
            value = experienceLevel,
            onValueChange = {experienceLevel = it},
            placeholder = {Text("Experience Level")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            runCatching {
                date = dateFormat.parse(birthday)
            }
            val user = UsersData(
                name = name,
                email = email,
                birthday = date?.time,
                goal = goal,
                weight = weightValue,
                desiredWeight = desiredWeightValue,
                height = heightValue,
                gender = gender,
                photoUrl = photoUrl,
                activityLevel = activityLevel,
                experienceLevel = experienceLevel
            )
            registerViewModel.addUser(user)
            Toast.makeText(context, "User add", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Register")
        }
    }
}