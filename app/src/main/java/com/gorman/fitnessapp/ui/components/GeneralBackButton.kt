package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun GeneralBackButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color
) {
    Row (
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = {onClick()}),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(painter = painterResource(R.drawable.arrow_back),
            contentDescription = "Arrow Back",
            tint = colorResource(R.color.meet_text),
            modifier = Modifier.height(15.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text,
            fontFamily = mulishFont(),
            fontSize = 20.sp,
            color = textColor,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(2.dp))
    }
}