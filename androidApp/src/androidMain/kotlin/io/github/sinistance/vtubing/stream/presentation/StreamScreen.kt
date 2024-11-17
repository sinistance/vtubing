package io.github.sinistance.vtubing.stream.presentation

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun StreamScreen(
    name: String,
    photoUrl: String,
    streamUrl: String,
    viewModel: StreamViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(streamUrl) {
        viewModel.fetchStream(streamUrl)
    }

    StreamScreenContent(
        uiState = uiState.value,
        name = name,
        photoUrl = photoUrl,
        streamUrl = streamUrl
    )
}

@Composable
fun StreamScreenContent(
    modifier: Modifier = Modifier,
    uiState: StreamUiState = StreamUiState(),
    name: String,
    photoUrl: String,
    streamUrl: String,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        StreamingScreen(
            name = name,
            photoUrl = photoUrl,
            streamingUrl = streamUrl,
        )
    }
}

@Composable
private fun StreamingScreen(
    name: String,
    photoUrl: String,
    streamingUrl: String,
) {
    val context = LocalContext.current
    var initialBuffering by remember { mutableStateOf(true) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(streamingUrl)
                    .setLiveConfiguration(
                        MediaItem.LiveConfiguration.Builder()
                            .setTargetOffsetMs(5000) // Set live latency to 5 seconds
                            .build()
                    )
                    .build()
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        when (playbackState) {
                            Player.STATE_READY -> initialBuffering = false
                            else -> { /* No action needed for other states */
                            }
                        }
                    }
                })
            }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    VideoPlayer(exoPlayer)
    if (initialBuffering) {
        LoadingScreen(
            name = name,
            photoUrl = photoUrl
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoPlayer(exoPlayer: ExoPlayer) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                resizeMode = RESIZE_MODE_ZOOM
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(bottom = 100.dp),
                text = "Loading stream..."
            )
            AsyncImage(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 10.dp),
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = name
            )
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StreamScreenContentPreview() {
    StreamScreenContent(
        uiState = StreamUiState(
            loading = true
        ),
        name = "huha",
        photoUrl = "ajsd",
        streamUrl = ""
    )
}