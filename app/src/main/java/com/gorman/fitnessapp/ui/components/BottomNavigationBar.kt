package com.gorman.fitnessapp.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<Screen.BottomScreen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    MainItem(
        items = items,
        currentRoute = currentRoute,
        navController = navController
    )
}

@Composable
fun MainItem(
    items: List<Screen.BottomScreen>,
    currentRoute: String?,
    navController: NavController
) {
    Card (
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.picker_wheel_bg)),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(24.dp)
    ){
        NavigationBar (
            modifier = Modifier.background(color = Color.Transparent),
            containerColor = Color.Transparent,
            windowInsets = NavigationBarDefaults.windowInsets
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(item.icon), contentDescription = stringResource(item.title)) },
                    selected = currentRoute == item.route,
                    colors = NavigationBarItemColors(
                        selectedIconColor = colorResource(R.color.font_purple_color),
                        selectedTextColor = Color.Black,
                        selectedIndicatorColor = colorResource(R.color.white),
                        unselectedIconColor = colorResource(R.color.white),
                        unselectedTextColor = colorResource(R.color.white),
                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent
                    ),
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        Log.d("ID", "${navController.graph.findStartDestination().id}")
                    }
                )
            }
        }
    }
}