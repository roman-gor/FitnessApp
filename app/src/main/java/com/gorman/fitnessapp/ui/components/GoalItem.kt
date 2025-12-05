package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun GoalItem(
    goalName: String,
    onClick: (String) -> Unit,
    isSelected: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = { onClick(goalName) }),
        shape = RoundedCornerShape(36.dp),
        border = BorderStroke(4.dp, Color.White),
        colors = CardDefaults.cardColors(
            containerColor =
                if (isSelected) colorResource(R.color.bg_color)
                else Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = goalName,
                fontFamily = mulishFont(),
                fontSize = 16.sp,
                color = if (!isSelected) colorResource(R.color.bg_color)
                        else Color.White,
                modifier = Modifier.padding(start = 16.dp)
            )
            Card (
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor =
                        if (!isSelected) colorResource(R.color.white)
                        else Color.Transparent
                ),
                border =
                    if (!isSelected)
                        BorderStroke(2.dp, colorResource(R.color.bg_color))
                    else null
            ) {
                if (isSelected) {
                    Image(
                        painter = painterResource(R.drawable.goal_selected),
                        contentDescription = "Selected Item",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(40.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalItemPreview() {
    GoalItem(goalName = "Goal", onClick = {}, isSelected = false)
}