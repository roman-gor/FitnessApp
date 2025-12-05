package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun DualButton(
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    leftTag: String,
    rightTag: String,
    containerColor: Color,
    textColor: Color,
    dividerColor: Color
) {
    Box (
        modifier = Modifier.width(280.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(18.dp))
            .background(containerColor)
            .clip(RoundedCornerShape(18.dp))
            .padding(20.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val width = size.width
                    if (offset.x < width/2)
                        onLeftClick()
                    else
                        onRightClick()
                }
            },
        contentAlignment = Alignment.Center
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = leftTag,
                fontFamily = mulishFont(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.width(4.dp)
                .fillMaxHeight(0.05f)
                .background(dividerColor))
            Text(
                text = rightTag,
                fontFamily = mulishFont(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = textColor
            )
        }
    }
}