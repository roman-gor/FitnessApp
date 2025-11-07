package com.gorman.fitnessapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeightPickerWheel(
    initialValue: Int,
    range: IntRange,
    onValueSelected: (Float) -> Unit
) {
    val values = range.toList()
    val subdivisions = 5
    val itemHeight = 90.dp
    val numberWidth = 90.dp
    val rulerWidth = 100.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val contentPadding = (screenHeight / 2) - (itemHeight / 2)

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
                val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2
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
        modifier = Modifier.wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn (
            state = listState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(vertical = contentPadding),
            modifier = Modifier
                .width(numberWidth + rulerWidth)
                .height(309.dp),
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(values) { index, value ->
                HeightRulerItem(
                    number = value,
                    isSelected = (index == centerIndex),
                    height = itemHeight,
                    rulerWidth = rulerWidth,
                    numberWidth = numberWidth,
                    subdivisions = subdivisions
                )
            }
        }
        val indicatorXOffset = rulerWidth / 2
        Spacer(
            modifier = Modifier
                .width(rulerWidth * 0.6f)
                .height(3.dp)
                .offset(x = indicatorXOffset + 3.dp)
                .background(colorResource(R.color.meet_text))
        )
    }
}


/**
 * Элемент списка (Число + блок с рисками)
 */
@Composable
fun HeightRulerItem(
    number: Int,
    isSelected: Boolean,
    height: Dp,
    rulerWidth: Dp,
    numberWidth: Dp,
    subdivisions: Int
) {
    val numberColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
    val fontSize = if (isSelected) 38.sp else 26.sp

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(height)
    ) {
        Text(
            text = number.toString(),
            color = numberColor,
            fontFamily = mulishFont(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = fontSize,
            modifier = Modifier.width(numberWidth),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(15.dp))
        Canvas(
            modifier = Modifier
                .width(rulerWidth)
                .height(height)
                .background(colorResource(R.color.picker_wheel_bg))
        ) {
            val tickHeightPx = size.height / subdivisions
            val smallTickWidth = size.width * 0.3f
            val largeTickWidth = size.width * 0.6f
            val tickColor = Color.White

            val largeTickXStart = (size.width - largeTickWidth) / 2
            val smallTickXStart = (size.width - smallTickWidth) / 2

            for (i in 0 until subdivisions) {
                val y = i * tickHeightPx + tickHeightPx / 2
                drawLine(
                    color = tickColor.copy(alpha = 0.5f),
                    start = Offset(x = smallTickXStart, y = y),
                    end = Offset(x = smallTickXStart + smallTickWidth, y = y),
                    strokeWidth = 1.dp.toPx()
                )
                if (i == 2)
                    drawLine(
                        color = tickColor,
                        start = Offset(x = largeTickXStart, y = y),
                        end = Offset(x = largeTickXStart + largeTickWidth, y = y),
                        strokeWidth = 2.dp.toPx()
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeightWheel() {
    MaterialTheme {
        HeightPickerWheel(initialValue = 150, range = IntRange(120,250)) { }
    }
}