package com.gorman.fitnessapp.ui.screens.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.fonts.mulishFont
import kotlinx.coroutines.launch

@Composable
fun InfoScreen(onSetupScreen: () -> Unit) {
    val pages = listOf(
        InfoPage(
            R.drawable.info_screen_icon_1,
            R.string.info_screen1,
            R.drawable.info_image1,
            R.string.next
        ),
        InfoPage(
            R.drawable.info_screen_icon_2,
            R.string.info_screen2,
            R.drawable.info_image2,
            R.string.next
        ),
        InfoPage(
            R.drawable.info_screen_icon_3,
            R.string.info_screen3,
            R.drawable.info_image3,
            R.string.get_started
        ),
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            InfoScreenTemplate(
                icon = pages[page].icon,
                text = pages[page].text,
                image = pages[page].image,
                buttonTitle = pages[page].buttonTitle,
                onNextPage = {
                    scope.launch {
                        val next = pagerState.currentPage + 1
                        if (next < pages.size) {
                            pagerState.animateScrollToPage(next)
                        } else {
                            onSetupScreen()
                        }
                    }
                }
            )
        }
    }

}

data class InfoPage(
    val icon: Int,
    val text: Int,
    val image: Int,
    val buttonTitle: Int
)

@Composable
fun InfoScreenTemplate(
    onNextPage: () -> Unit,
    buttonTitle: Int,
    icon: Int,
    text: Int,
    image: Int
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(color = colorResource(R.color.picker_wheel_bg)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = colorResource(R.color.meet_text)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(text),
                        fontFamily = mulishFont(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNextPage() },
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(36.dp),
                modifier = Modifier.width(200.dp)
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                ),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
            ) {
                Text(text = stringResource(buttonTitle),
                    fontFamily = mulishFont(),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(R.color.white),
                    modifier = Modifier
                        .padding(8.dp))
            }
        }
    }
}