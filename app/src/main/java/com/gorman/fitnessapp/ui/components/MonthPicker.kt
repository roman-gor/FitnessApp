package com.gorman.fitnessapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPicker(
    visible: Boolean,
    currentMonth: Int,
    currentYear: Int,
    confirmButtonClicked: (Int, Int) -> Unit,
    cancelClicked: () -> Unit
) {

    val months = listOf(
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC"
    )

    var month by remember { mutableStateOf(months[currentMonth]) }
    var year by remember { mutableIntStateOf(currentYear) }
    var yearDirection by remember { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }

    if (visible) {
        BasicAlertDialog(
            onDismissRequest = {
                cancelClicked()
            },
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF2E2D38)),
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true),
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year--
                                        yearDirection = -1
                                    }
                                ),
                            tint = Color.White,
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null
                        )
                        AnimatedContent(
                            targetState = year,
                            transitionSpec = {
                                val duration = 300
                                if (yearDirection > 0) {
                                    slideInHorizontally(animationSpec = tween(duration)) { fullWidth -> fullWidth } togetherWith
                                            slideOutHorizontally(animationSpec = tween(duration)) { fullWidth -> -fullWidth }
                                } else {
                                    slideInHorizontally(animationSpec = tween(duration)) { fullWidth -> -fullWidth } togetherWith
                                            slideOutHorizontally(animationSpec = tween(duration)) { fullWidth -> fullWidth }
                                }
                            },
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) { targetYear ->
                            Text(
                                text = targetYear.toString(),
                                fontSize = 24.sp,
                                modifier = Modifier.width(120.dp),
                                fontFamily = mulishFont(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(-90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year++
                                        yearDirection = 1
                                    }
                                ),
                            tint = Color.White,
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null
                        )

                    }
                    Card(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2E2D38)
                        )
                    ) {

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisSpacing = 16.dp,
                            crossAxisSpacing = 16.dp,
                            mainAxisAlignment = MainAxisAlignment.Center,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                        ) {

                            months.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = {
                                                month = it
                                                confirmButtonClicked(months.indexOf(month) + 1, year)
                                                cancelClicked()
                                            }
                                        )
                                        .background(
                                            color = Color.Transparent
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    val animatedSize by animateDpAsState(
                                        targetValue = if (month == it) 50.dp else 0.dp,
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(animatedSize)
                                            .background(
                                                color = if (month == it) colorResource(R.color.meet_text) else Color.Transparent,
                                                shape = CircleShape
                                            )
                                    )
                                    Text(
                                        text = it,
                                        fontSize = 16.sp,
                                        color = if (month == it) Color.Black else Color.White,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = mulishFont(),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun CalendarPreview() {
    MaterialTheme {
        MonthPicker(true, 11, 2025, { _, _ ->}, {})
    }
}