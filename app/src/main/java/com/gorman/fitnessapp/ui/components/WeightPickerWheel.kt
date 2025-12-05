package com.gorman.fitnessapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
fun WeightPickerWheel(
    initialValue: Int,
    range: IntRange,
    onValueSelected: (Float) -> Unit
) {
    val values = range.toList()
    val subdivisions = 5

    val itemWidth = 90.dp
    val numberHeight = 40.dp
    val rulerHeight = 80.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentPadding = (screenWidth / 2) - (itemWidth / 2)

    val initialIndex = values.indexOf(initialValue).coerceAtLeast(0)
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
        if (centerIndex != -1 && centerIndex < values.size) {
            val selectedValue = values[centerIndex]
            onValueSelected(selectedValue.toFloat())
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = listState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(horizontal = contentPadding),
            modifier = Modifier
                .fillMaxWidth()
                .height(numberHeight + rulerHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(values) { index, value ->
                RulerItem(
                    number = value,
                    isSelected = (index == centerIndex),
                    width = itemWidth,
                    rulerHeight = rulerHeight,
                    numberHeight = numberHeight,
                    subdivisions = subdivisions
                )
            }
        }
        val indicatorYOffset = numberHeight / 2
        Spacer(
            modifier = Modifier
                .width(3.dp)
                .height(rulerHeight)
                .offset(y = indicatorYOffset)
                .background(colorResource(R.color.meet_text))
        )
    }
}


/**
 * Элемент списка (Число + блок с рисками)
 */
@Composable
fun RulerItem(
    number: Int,
    isSelected: Boolean,
    width: Dp,
    rulerHeight: Dp,
    numberHeight: Dp,
    subdivisions: Int
) {
    val numberColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
    val fontSize = if (isSelected) 32.sp else 24.sp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(width)
    ) {
        Text(
            text = number.toString(),
            color = numberColor,
            fontFamily = mulishFont(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = fontSize,
            modifier = Modifier.height(numberHeight)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Canvas(
            modifier = Modifier
                .width(width)
                .height(rulerHeight)
                .background(colorResource(R.color.picker_wheel_bg))
        ) {
            val tickWidthPx = size.width / subdivisions
            val smallTickHeight = size.height * 0.3f
            val largeTickHeight = size.height * 0.6f
            val tickColor = Color.White
            val largeTickYStart = (size.height - largeTickHeight) / 2
            val smallTickYStart = (size.height - smallTickHeight) / 2

            for (i in 0 until subdivisions) {
                val x = i * tickWidthPx + tickWidthPx/2
                drawLine(
                    color = tickColor.copy(alpha = 0.5f),
                    start = Offset(x = x, y = smallTickYStart),
                    end = Offset(x = x, y = smallTickYStart + smallTickHeight),
                    strokeWidth = 1.dp.toPx()
                )
                if (i == 2)
                    drawLine(
                        color = tickColor,
                        start = Offset(x = x, y = largeTickYStart),
                        end = Offset(x = x, y = largeTickYStart + largeTickHeight),
                        strokeWidth = 2.dp.toPx()
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeightWheel() {
    MaterialTheme {
        WeightPickerWheel(initialValue = 50, range = IntRange(10,200)) { }
    }
}