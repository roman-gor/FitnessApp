package com.gorman.fitnessapp.ui.screens.workout

import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.ui.components.Header
import com.gorman.fitnessapp.ui.fonts.mulishFont

@Composable
fun ExerciseByDayScreen(
    onBackPage: () -> Unit,
    exercise: Exercise
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isPlaying by remember { mutableStateOf(false) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(exercise.videoUrl!!)
            setMediaItem(mediaItem)
            prepare()
            volume = 0f
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_ALL
        }
    }
    Log.d("Exercise", exercise.toString())
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                exoPlayer.pause()
                isPlaying = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(colorResource(R.color.bg_color))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(
                onBackPage = onBackPage,
                text = stringResource(R.string.exercise)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.picker_wheel_bg)),
                contentAlignment = Alignment.Center
            ) {
                VideoItem(exoPlayer) {
                    if (isPlaying)
                        exoPlayer.pause()
                    else
                        exoPlayer.play()
                    isPlaying = !isPlaying
                }
                if (!isPlaying) {
                    PlayButton {
                        isPlaying = !isPlaying
                    }
                }
            }
            ExerciseDescription(exercise = exercise)
        }
    }
}

@OptIn(UnstableApi::class)
@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@Composable
fun VideoItem(
    exoPlayer: ExoPlayer,
    onPlayClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(470.dp)
            .padding(horizontal = 32.dp, vertical = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(R.color.black))
    ) {
        AndroidView(
            factory = { ctx->
                val view = LayoutInflater.from(ctx)
                    .inflate(R.layout.layout_player_view, null, false)
                val playerView = view.findViewById<PlayerView>(R.id.player_view)
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                playerView.apply {
                    player = exoPlayer
                    setOnClickListener { onPlayClick() }
                }
                playerView
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PlayButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.size(100.dp).clip(CircleShape),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(painter = painterResource(R.drawable.play_button),
            contentDescription = "Play button")
    }
}

@Composable
fun ExerciseDescription(
    exercise: Exercise
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.meet_text)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = exercise.name,
                    fontFamily = mulishFont(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.bg_color),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                exercise.description?.let {
                    Text(
                        text = it,
                        fontFamily = mulishFont(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.bg_color),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}