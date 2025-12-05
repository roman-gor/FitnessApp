package com.gorman.fitnessapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgePickerWheel(
    initialAge: Int,
    ageRange: IntRange,
    onAgeSelected: (Long) -> Unit
) {
    val ages = ageRange.toList()

    val itemWidth = 80.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val haptic = LocalHapticFeedback.current
    val contentPadding = (screenWidth / 2) - (itemWidth / 2)
    val initialIndex = ages.indexOf(initialAge).coerceAtLeast(0)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val centerIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                -1
            } else {
                val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2
                visibleItems.minByOrNull {
                    kotlin.math.abs((it.offset + it.size / 2) - viewportCenter)
                }?.index ?: -1
            }
        }
    }

    LaunchedEffect(centerIndex) {
        if (centerIndex != -1 && centerIndex < ages.size) {
            val selectedAge = ages[centerIndex]
            onAgeSelected(selectedAge.toLong())
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = listState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(horizontal = contentPadding),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(ages) { index, age ->
                AgeItem(
                    age = age,
                    isSelected = (index == centerIndex),
                    width = itemWidth
                )
            }
        }
        AgePickerDecoration(itemWidth = itemWidth)
    }
}

/**
 * Элемент списка (число)
 */
@Composable
fun AgeItem(age: Int, isSelected: Boolean, width: Dp) {
    val fontColor = if (isSelected) Color.White else colorResource(R.color.bg_color)
    val fontSize = if (isSelected) 32.sp else 24.sp

    Box(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .background(colorResource(R.color.picker_wheel_bg)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = age.toString(),
            fontFamily = mulishFont(),
            color = fontColor,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

/**
 * Центральные линии
 */
@Composable
fun AgePickerDecoration(itemWidth: Dp) {
    Box(
        modifier = Modifier
            .width(itemWidth)
            .height(110.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(1f)
                .background(Color.White.copy(alpha = 0.8f)))
            Spacer(modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(1f)
                .background(Color.White.copy(alpha = 0.8f)))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PickerPreview() {
    AgePickerWheel(initialAge = 18,
        ageRange = IntRange(16,100)) { }
}