package com.gorman.fitnessapp.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.fonts.mulishFont
import com.gorman.fitnessapp.ui.screens.setup.ErrorMessage
import com.gorman.fitnessapp.ui.states.ArticlesState
import com.gorman.fitnessapp.ui.states.HomeUiState
import com.gorman.fitnessapp.ui.viewmodel.HomeViewModel
import kotlin.math.ceil
import kotlin.math.max

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onProfileClick: (UsersData) -> Unit,
    onWorkoutClick: (UsersData) -> Unit,
    onProgressClick: () -> Unit,
    onNutritionClick: () -> Unit,
    onNavigateToGenProgram: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.prepareData()
    }
    val isProgramExisting by homeViewModel.programExistingState
    val articlesList by homeViewModel.articleListState
    val userData by homeViewModel.userDataState
    val uiState by homeViewModel.homeUiState
    val articlesState by homeViewModel.articlesUiState
    when (val state = uiState) {
        is HomeUiState.Error -> {
            LaunchedEffect(state) {
                Toast.makeText(context, "Ошибка при загрузке данных: ${state.message}", Toast.LENGTH_LONG).show()
            }
            GeneralScreen(
                onProfileClick = onProfileClick,
                onWorkoutClick = onWorkoutClick,
                onProgressClick = onProgressClick,
                onNutritionClick = onNutritionClick,
                isProgramExisting = isProgramExisting,
                articlesList = articlesList,
                userData = userData,
                onNavigateToGenProgram = onNavigateToGenProgram,
                articlesState = articlesState
            )
        }
        HomeUiState.Idle -> {
            LoadingStub()
        }
        HomeUiState.Loading -> {
            LoadingStub()
        }
        HomeUiState.Success -> {
            GeneralScreen(
                onProfileClick = onProfileClick,
                onWorkoutClick = {
                    if (isProgramExisting)
                        onWorkoutClick(it)
                },
                onProgressClick = onProgressClick,
                onNutritionClick = onNutritionClick,
                isProgramExisting = isProgramExisting,
                articlesList = articlesList,
                userData = userData,
                onNavigateToGenProgram = onNavigateToGenProgram,
                articlesState = articlesState
            )
        }
    }
}

@Composable
fun GeneralScreen(
    onProfileClick: (UsersData) -> Unit,
    onWorkoutClick: (UsersData) -> Unit,
    onProgressClick: () -> Unit,
    onNutritionClick: () -> Unit,
    isProgramExisting: Boolean,
    articlesList: List<Article>,
    userData: UsersData?,
    onNavigateToGenProgram: () -> Unit,
    articlesState: ArticlesState
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.bg_color))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(onProfileClick = onProfileClick, userData)
                Spacer(modifier = Modifier.height(20.dp))
                HomeNavigation(
                    onWorkoutClick = { userData?.let { onWorkoutClick(it) } },
                    onProgressClick = { onProgressClick() },
                    onNutritionClick = { onNutritionClick() })
            }
            ProgramCard(
                isProgramExists = isProgramExisting,
                onGenerateClick = { onNavigateToGenProgram() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ArticleList(
                items = articlesList,
                state = articlesState)
        }
    }
}

@Composable
fun Header(
    onProfileClick: (UsersData) -> Unit,
    userData: UsersData?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            userData?.name?.let {
                Text(
                    text = "${stringResource(R.string.hello)}, $it",
                    fontFamily = mulishFont(),
                    fontSize = 22.sp,
                    color = colorResource(R.color.picker_wheel_bg),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.header_text),
                fontFamily = mulishFont(),
                fontSize = 12.sp,
                color = colorResource(R.color.picker_wheel_bg),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton (
            onClick = {
                userData?.let { onProfileClick(it)  } },
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.profile_icon),
                contentDescription = "Profile Icon",
                tint = colorResource(R.color.picker_wheel_bg),
                modifier = Modifier
                    .scale(1.4f)
                    .padding(8.dp))
        }
    }
}

@Composable
fun HomeNavigation(
    onWorkoutClick: () -> Unit,
    onProgressClick: () -> Unit,
    onNutritionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onWorkoutClick() }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.workout_icon),
                contentDescription = "Workout Button",
                tint = colorResource(R.color.meet_text),
                modifier = Modifier.scale(1.4f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.workout),
                fontFamily = mulishFont(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.meet_text),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier
            .height(40.dp)
            .width(2.dp)
            .background(Color.White.copy(alpha = 0.5f)))
        Column(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onProgressClick() }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.progress_tracking_icon),
                contentDescription = "Workout Button",
                tint = colorResource(R.color.meet_text),
                modifier = Modifier.scale(1.4f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.progress),
                fontFamily = mulishFont(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.meet_text),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier
            .height(40.dp)
            .width(2.dp)
            .background(Color.White.copy(alpha = 0.5f)))
        Column(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onNutritionClick() }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.nutrition_icon),
                contentDescription = "Workout Button",
                tint = colorResource(R.color.meet_text),
                modifier = Modifier.scale(1.4f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.nutrition),
                fontFamily = mulishFont(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.meet_text),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ProgramCard(
    isProgramExists: Boolean,
    onGenerateClick: () -> Unit
) {
    val descText = if (isProgramExists) stringResource(R.string.program_desc_text)
        else stringResource(R.string.program_gen_description_text)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.workout_program),
            fontFamily = mulishFont(),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            color = colorResource(R.color.meet_text)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.picker_wheel_bg)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 40.dp)
                    .clickable(
                        onClick = { onGenerateClick() }
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.bg_color)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.4f)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.program_header_text),
                            fontSize = 26.sp,
                            color = colorResource(R.color.meet_text),
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = mulishFont(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = descText,
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = mulishFont(),
                            textAlign = TextAlign.Center
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.program_image),
                        contentDescription = "Woman exercising in a gym",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(140.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    items: List<Article>,
    state: ArticlesState
) {
    val itemHeight = 230.dp
    val gridSpacing = 8.dp
    val rows = ceil(items.size / 2.0).toInt()
    val gridHeight = (itemHeight * rows) + (gridSpacing * max(0, rows - 1))
    when (state) {
        is ArticlesState.Error -> {
            Text(
                text = "Ошибка загрузки статей: ${state.message}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp),
                fontFamily = mulishFont())
            return
        }
        ArticlesState.Loading -> {
            LoadingStub(size = 100.dp)
            return
        }
        ArticlesState.Success -> {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.articles),
                    fontFamily = mulishFont(),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    color = colorResource(R.color.meet_text)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (state == ArticlesState.Loading)
                LoadingStub()
            LazyVerticalGrid (
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridHeight)
                    .padding(horizontal = 16.dp),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    ArticleListItem(item) {  }
                }
            }
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article,
    onItemClick: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(article) })
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Firebase Image",
            placeholder = painterResource(R.drawable.articles_placeholder),
            error = painterResource(R.drawable.articles_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.title,
            fontFamily = mulishFont(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    //HomeScreen {  }
}