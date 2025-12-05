package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun DefaultOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier) {
    val placeholderTextColor = colorResource(R.color.bg_color).copy(alpha = 0.5f)
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = TextStyle(
            fontFamily = mulishFont(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorResource(R.color.bg_color),
            unfocusedTextColor = colorResource(R.color.bg_color).copy(alpha = 0.5f),
            unfocusedContainerColor = colorResource(R.color.white),
            focusedContainerColor = colorResource(R.color.white)
        ),
        placeholder = {
            if (placeholder != null) {
                Text(
                    text = placeholder,
                    fontFamily = mulishFont(),
                    fontWeight = FontWeight.Medium,
                    color = placeholderTextColor,
                    fontSize = 18.sp
                )
            }
        },
        shape = RoundedCornerShape(24.dp),
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        singleLine = true
    )
}