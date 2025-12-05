package com.gorman.fitnessapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData

@Composable
fun Header(
    onBackPage: () -> Unit,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp, start = 28.dp, end = 20.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            GeneralBackButton(
                onClick = { onBackPage() },
                text = text,
                textColor = colorResource(R.color.picker_wheel_bg)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}