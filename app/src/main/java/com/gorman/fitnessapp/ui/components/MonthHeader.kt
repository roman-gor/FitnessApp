package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeekdaysRow() {
    val weekdays = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        weekdays.forEach { dayOfWeek ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colorResource(R.color.font_purple_color)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        id = processWeekDay(
                            day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        )
                    ),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = mulishFont(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    Spacer(Modifier.height(12.dp))
}

fun processWeekDay(day: String): Int {
    return when(day) {
        "Mon" -> R.string.mon
        "Tue" -> R.string.tue
        "Wed" -> R.string.wed
        "Thu" -> R.string.thu
        "Fri" -> R.string.fri
        "Sat" -> R.string.sat
        "Sun" -> R.string.sun
        else -> R.string.mon
    }
}