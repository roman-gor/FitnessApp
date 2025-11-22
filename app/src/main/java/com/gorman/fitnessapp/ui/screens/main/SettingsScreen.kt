
package com.gorman.fitnessapp.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.GeneralBackButton
import com.gorman.fitnessapp.ui.components.RoundedButton
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.viewmodel.ProfileViewModel

@Composable
fun SettingsScreen(
    usersData: UsersData,
    onBackPage: () -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()
        .background(colorResource(R.color.bg_color))) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(colorResource(R.color.bg_color)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 28.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                GeneralBackButton(
                    onClick = { onBackPage() },
                    text = stringResource(R.string.profile_title),
                    textColor = colorResource(R.color.picker_wheel_bg)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable(
                        onClick = {
                            showDialog = !showDialog
                        }
                    )
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_edit_icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(R.string.delete_account),
                        fontFamily = mulishFont(),
                        fontSize = 16.sp,
                        color = colorResource(R.color.white),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier.rotate(270f),
                    tint = colorResource(R.color.meet_text)
                )
            }
        }
        if (showDialog) {
            Dialog(
                onDismiss = {showDialog = !showDialog},
                onConfirm = {profileViewModel.deleteAccount(usersData)}
            )
        }
    }
}

@Composable
fun Dialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()
        .background(colorResource(R.color.bg_color).copy(alpha = 0.5f))
        .clickable(
            indication = null,
            interactionSource = null,
            onClick = { onDismiss() }
        ),
        contentAlignment = Alignment.BottomCenter) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.white)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.delete_confirm),
                    fontFamily = mulishFont(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.bg_color),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
                Row (
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ){
                    RoundedButton(
                        onClick = {
                            onDismiss() },
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.picker_wheel_bg),
                        text = R.string.cancel,
                        textColor = colorResource(R.color.white))
                    RoundedButton(onClick = {
                        onDismiss()
                        onConfirm() },
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.meet_text),
                        text = R.string.confirm,
                        textColor = colorResource(R.color.bg_color))
                }
            }
        }
    }
}