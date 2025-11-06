package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun SetupNextButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        modifier = Modifier.width(180.dp).height(56.dp)
    ) {
        Text(text = text,
            fontFamily = mulishFont(),
            color = Color.White,
            fontSize = 18.sp)
    }
    Spacer(modifier = Modifier.height(60.dp))
}