package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun DatesGrid(
    currentMonth: LocalDate,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.withDayOfMonth(1)
    val startOffset = firstDayOfMonth.dayOfWeek.value - DayOfWeek.MONDAY.value

    val daysInMonth = currentMonth.lengthOfMonth()

    val dates = buildList {
        repeat(startOffset) { add(null) }
        for (i in 1..daysInMonth) {
            add(currentMonth.withDayOfMonth(i))
        }
    }

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(dates) { date ->
                if (date == null) {
                    Spacer(modifier = Modifier.size(25.dp))
                } else {
                    DateItem(
                        date = date,
                        isSelected = date == selectedDate,
                        onClick = { onDateSelected(date) }
                    )
                }
            }
        }
    }
}

@Composable
fun DateItem(date: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) colorResource(R.color.meet_text) else Color.Transparent
    val textColor = if (isSelected) Color.Black else Color.White
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(25.dp)
            .aspectRatio(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(25.dp)
                .background(backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}