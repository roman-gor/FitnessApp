package com.gorman.fitnessapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.ui.screens.RegisterScreen
import com.gorman.fitnessapp.ui.theme.FitnessAppTheme
import com.gorman.fitnessapp.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            val users = registerViewModel.usersState.value
            val json = registerViewModel.json.value
            val state = rememberScrollState()
            LaunchedEffect(Unit) {
                registerViewModel.prompt()
            }
            FitnessAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())){
                        SelectionContainer {
                            Text(json)
                        }
                    }
                    //RegisterScreen()
                    if (users.isNotEmpty()) {
                        Log.d("Room", users[1].toString())
                    } else {
                        Log.d("Room", "Users list is empty or still loading.")
                    }
                }
            }
        }
    }
}