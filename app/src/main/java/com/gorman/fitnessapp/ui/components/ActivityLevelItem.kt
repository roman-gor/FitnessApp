package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun ActivityLevelItem(
    title: String,
    onClick: (String) -> Unit,
    isSelected: Boolean
) {
    Card (
        shape = RoundedCornerShape(36.dp),
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = {onClick(title)}),
        colors = CardDefaults.cardColors(
            containerColor =
                if (isSelected) colorResource(R.color.meet_text)
                else Color.White
        )
    ){
        Text(
            text = title,
            fontFamily = mulishFont(),
            fontWeight = FontWeight.ExtraBold,
            color = if (isSelected) colorResource(R.color.bg_color)
                else colorResource(R.color.font_purple_color),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}