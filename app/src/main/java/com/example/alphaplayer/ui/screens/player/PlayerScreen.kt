//package com.example.alphaplayer.ui.screens.player
//
//import android.content.Context
//import android.content.pm.ActivityInfo
//import android.content.res.Configuration
//import android.media.AudioManager
//import android.view.ViewGroup
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.BackHandler
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.gestures.detectVerticalDragGestures
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ClosedCaption
//import androidx.compose.material.icons.filled.Fullscreen
//import androidx.compose.material.icons.filled.FullscreenExit
//import androidx.compose.material.icons.filled.Pause
//import androidx.compose.material.icons.filled.PictureInPictureAlt
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material.icons.filled.SkipNext
//import androidx.compose.material.icons.filled.SkipPrevious
//import androidx.compose.material.icons.filled.VolumeMute
//import androidx.compose.material.icons.filled.VolumeUp
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Slider
//import androidx.compose.material3.SliderDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableFloatStateOf
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableLongStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.view.WindowCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.core.view.WindowInsetsControllerCompat
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleEventObserver
//import androidx.media3.common.C
//import androidx.media3.common.MediaItem
//import androidx.media3.common.PlaybackException
//import androidx.media3.common.Player
//import androidx.media3.common.TrackSelectionOverride
//import androidx.media3.common.Tracks
//import androidx.media3.common.util.UnstableApi
//import androidx.media3.datasource.DefaultHttpDataSource
//import androidx.media3.exoplayer.DefaultLoadControl
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
//import androidx.media3.ui.AspectRatioFrameLayout
//import androidx.media3.ui.PlayerView
//import androidx.navigation.NavController
//import kotlinx.coroutines.delay
//import java.util.concurrent.TimeUnit
//import kotlin.math.max
//import kotlin.math.min
//
//@androidx.annotation.OptIn(UnstableApi::class)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PlayerScreen(
//    title: String,
//    url: String,
//    navController: NavController,
//    headersJson: String? = null
//) {
//    val headers = remember(headersJson) {
//        if (headersJson == null) null
//        else try {
//            kotlinx.serialization.json.Json.decodeFromString<Map<String, String>>(headersJson)
//        } catch (e: Exception) {
//            null
//        }
//    }
//    val context = LocalContext.current
//    val activity = context as? ComponentActivity
//    val configuration = LocalConfiguration.current
//    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    LaunchedEffect(Unit) {
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
//    }
//
//    var isBarsVisible by remember { mutableStateOf(false) }
//    var isPlayingState by remember { mutableStateOf(true) }
//    var showSpeedSheet by remember { mutableStateOf(false) }
//    var showSubtitleSheet by remember { mutableStateOf(false) }
//    var currentPlaybackSpeed by remember { mutableFloatStateOf(1.0f) }
//
//    var currentTracks by remember { mutableStateOf<Tracks?>(null) }
//    var isSubtitlesEnabled by remember { mutableStateOf(true) }
//
//    var savedPlaybackPositionMs by rememberSaveable { mutableLongStateOf(0L) }
//    var currentPositionMs by remember { mutableLongStateOf(savedPlaybackPositionMs) }
//    var totalDurationMs by remember { mutableLongStateOf(0L) }
//
//    var resizeModeState by rememberSaveable {
//        mutableIntStateOf(if (isLandscape) AspectRatioFrameLayout.RESIZE_MODE_FILL else AspectRatioFrameLayout.RESIZE_MODE_FIT)
//    }
//
//    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
//    val maxVolume = remember { audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat() }
//    var currentVolume by remember { mutableFloatStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()) }
//    var isMuted by remember { mutableStateOf(false) }
//    var currentBrightness by remember {
//        mutableFloatStateOf(
//            activity?.window?.attributes?.screenBrightness.takeIf { it != null && it >= 0 } ?: 0.5f
//        )
//    }
//
//    val loadControl = remember {
//        DefaultLoadControl.Builder()
//            .setBufferDurationsMs(10000, 50000, 1500, 3000)
//            .build()
//    }
//
//    val exoPlayer = remember(url, headers) {
//        val targetUrl = url.trim()
//
//        val httpDataSourceFactory = DefaultHttpDataSource.Factory().apply {
//            val userAgent = headers?.get("User-Agent") ?: "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
//            setUserAgent(userAgent)
//
//            val requestProperties = mutableMapOf<String, String>()
//
//            // Dynamic Referer Binding
//            requestProperties["Referer"] = headers?.get("Referer") ?: when {
//                targetUrl.contains("github") -> "https://github.com/"
//                targetUrl.contains("fibwatch") -> "https://fibwatch.art/"
//                else -> "https://google.com/"
//            }
//
//            headers?.forEach { (key, value) ->
//                if (key != "User-Agent" && key != "Referer") {
//                    requestProperties[key] = value
//                }
//            }
//            setDefaultRequestProperties(requestProperties)
//        }
//
//        val mediaSourceFactory = DefaultMediaSourceFactory(context)
//            .setDataSourceFactory(httpDataSourceFactory)
//
//        ExoPlayer.Builder(context)
//            .setMediaSourceFactory(mediaSourceFactory)
//            .setLoadControl(loadControl)
//            .setSeekBackIncrementMs(10000)
//            .setSeekForwardIncrementMs(10000)
//            .build()
//            .apply {
//                val finalVideoUri = if (targetUrl.contains("|Referer=")) {
//                    targetUrl.substringBefore("|")
//                } else {
//                    targetUrl
//                }
//
//                val mediaItem = MediaItem.Builder().setUri(finalVideoUri).build()
//                setMediaItem(mediaItem)
//
//                if (savedPlaybackPositionMs > 0L) {
//                    seekTo(savedPlaybackPositionMs)
//                }
//
//                repeatMode = Player.REPEAT_MODE_OFF
//                playWhenReady = true
//                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
//                prepare()
//
//                addListener(object : Player.Listener {
//                    override fun onPlayerError(error: PlaybackException) {
//                        error.printStackTrace()
//                    }
//
//                    override fun onIsPlayingChanged(isPlaying: Boolean) {
//                        super.onIsPlayingChanged(isPlaying)
//                        isPlayingState = isPlaying
//                        isBarsVisible = !isPlaying
//                    }
//
//                    override fun onTracksChanged(tracks: Tracks) {
//                        super.onTracksChanged(tracks)
//                        currentTracks = tracks
//                    }
//
//                    override fun onPlaybackStateChanged(playbackState: Int) {
//                        super.onPlaybackStateChanged(playbackState)
//                        if (playbackState == Player.STATE_READY) {
//                            totalDurationMs = duration.coerceAtLeast(0L)
//                        }
//                    }
//                })
//            }
//    }
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_PAUSE) {
//                exoPlayer.pause()
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }
//
//    LaunchedEffect(isPlayingState) {
//        while (isPlayingState) {
//            currentPositionMs = exoPlayer.currentPosition.coerceAtLeast(0L)
//            savedPlaybackPositionMs = currentPositionMs
//            totalDurationMs = exoPlayer.duration.coerceAtLeast(0L)
//            delay(500)
//        }
//    }
//
//    DisposableEffect(isBarsVisible, isLandscape) {
//        val window = activity?.window
//        val insetsController = window?.let { WindowCompat.getInsetsController(it, it.decorView) }
//
//        insetsController?.apply {
//            if (!isBarsVisible || isLandscape) {
//                hide(WindowInsetsCompat.Type.systemBars())
//                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            } else {
//                show(WindowInsetsCompat.Type.systemBars())
//            }
//        }
//
//        onDispose {
//            insetsController?.show(WindowInsetsCompat.Type.systemBars())
//        }
//    }
//
//    DisposableEffect(exoPlayer) {
//        onDispose {
//            savedPlaybackPositionMs = exoPlayer.currentPosition
//            exoPlayer.release()
//        }
//    }
//
//    BackHandler {
//        navController.popBackStack()
//    }
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        containerColor = Color.Black
//    ) { innerPadding ->
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black)
//        ) {
//            AndroidView(
//                modifier = Modifier.fillMaxSize(),
//                factory = { ctx ->
//                    PlayerView(ctx).apply {
//                        player = exoPlayer
//                        useController = false
//                        keepScreenOn = true
//                        setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
//                        resizeMode = resizeModeState
//
//                        layoutParams = ViewGroup.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                        )
//                    }
//                },
//                update = { view ->
//                    view.resizeMode = resizeModeState
//                }
//            )
//
//            // Brightness & Volume Vertical Gesture Drivers
//            Row(modifier = Modifier.fillMaxSize()) {
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxHeight()
//                        .pointerInput(Unit) {
//                            detectVerticalDragGestures { _, dragAmount ->
//                                val delta = -dragAmount / 500f
//                                currentBrightness = min(1f, max(0.01f, currentBrightness + delta))
//                                activity?.window?.let { win ->
//                                    val layoutParams = win.attributes
//                                    layoutParams.screenBrightness = currentBrightness
//                                    win.attributes = layoutParams
//                                }
//                            }
//                        }
//                )
//
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxHeight()
//                        .pointerInput(Unit) {
//                            detectVerticalDragGestures { _, dragAmount ->
//                                val delta = -dragAmount / 30f
//                                currentVolume = min(maxVolume, max(0f, currentVolume + delta))
//                                audioManager.setStreamVolume(
//                                    AudioManager.STREAM_MUSIC,
//                                    currentVolume.toInt(),
//                                    AudioManager.FLAG_SHOW_UI
//                                )
//                            }
//                        }
//                )
//            }
//
//            // Tap Overlay to Toggle Player Controls
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .pointerInput(Unit) {
//                        detectTapGestures(
//                            onTap = {
//                                isBarsVisible = !isBarsVisible
//                            }
//                        )
//                    }
//            )
//
//            AnimatedVisibility(
//                visible = isBarsVisible,
//                enter = fadeIn(),
//                exit = fadeOut()
//            ) {
//                Box(modifier = Modifier.fillMaxSize()) {
//
//                    // TOP BAR
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .align(Alignment.TopCenter)
//                            .background(
//                                Brush.verticalGradient(
//                                    colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
//                                )
//                            )
//                    ) {
//                        CenterAlignedTopAppBar(
//                            modifier = Modifier.statusBarsPadding(),
//                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                                containerColor = Color.Transparent,
//                                titleContentColor = Color.White,
//                                navigationIconContentColor = Color.White
//                            ),
//                            title = {
//                                Text(
//                                    text = title,
//                                    maxLines = 1,
//                                    overflow = TextOverflow.Ellipsis
//                                )
//                            },
//                            navigationIcon = {
//                                IconButton(onClick = { navController.popBackStack() }) {
//                                    Icon(
//                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                                        contentDescription = "Back"
//                                    )
//                                }
//                            }
//                        )
//                    }
//
//                    // CENTER RED PLAY/PAUSE BUTTON
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .size(64.dp)
//                            .clip(CircleShape)
//                            .background(Color.Red)
//                            .clickable {
//                                if (exoPlayer.isPlaying) {
//                                    exoPlayer.pause()
//                                } else {
//                                    exoPlayer.play()
//                                }
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = if (isPlayingState) Icons.Default.Pause else Icons.Default.PlayArrow,
//                            contentDescription = "Play/Pause",
//                            tint = Color.White,
//                            modifier = Modifier.size(40.dp)
//                        )
//                    }
//
//                    // BOTTOM CONTROL BAR
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .align(Alignment.BottomCenter)
//                            .background(
//                                Brush.verticalGradient(
//                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f))
//                                )
//                            )
//                            .navigationBarsPadding()
//                    ) {
//                        // ULTRA-THIN CUSTOM TRACK SEEKBAR
//                        Slider(
//                            value = currentPositionMs.toFloat(),
//                            onValueChange = { newPos ->
//                                currentPositionMs = newPos.toLong()
//                                savedPlaybackPositionMs = currentPositionMs
//                                exoPlayer.seekTo(currentPositionMs)
//                            },
//                            valueRange = 0f..(totalDurationMs.coerceAtLeast(1L)).toFloat(),
//                            thumb = {
//                                Box(
//                                    modifier = Modifier
//                                        .size(10.dp)
//                                        .clip(CircleShape)
//                                        .background(Color.Red)
//                                )
//                            },
//                            track = { sliderState ->
//                                SliderDefaults.Track(
//                                    sliderState = sliderState,
//                                    modifier = Modifier.height(2.dp),
//                                    colors = SliderDefaults.colors(
//                                        activeTrackColor = Color.Red,
//                                        inactiveTrackColor = Color.White.copy(alpha = 0.35f)
//                                    )
//                                )
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 12.dp)
//                        )
//
//                        // CONTROLS ROW
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 12.dp, vertical = 2.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                IconButton(
//                                    onClick = { exoPlayer.seekBack() },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.SkipPrevious,
//                                        contentDescription = "Previous",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                IconButton(
//                                    onClick = {
//                                        if (exoPlayer.isPlaying) {
//                                            exoPlayer.pause()
//                                        } else {
//                                            exoPlayer.play()
//                                        }
//                                    },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = if (isPlayingState) Icons.Default.Pause else Icons.Default.PlayArrow,
//                                        contentDescription = "Play/Pause",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                IconButton(
//                                    onClick = { exoPlayer.seekForward() },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.SkipNext,
//                                        contentDescription = "Next",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                IconButton(
//                                    onClick = {
//                                        isMuted = !isMuted
//                                        exoPlayer.volume = if (isMuted) 0f else 1f
//                                    },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
//                                        contentDescription = "Volume",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                Spacer(modifier = Modifier.width(6.dp))
//
//                                Text(
//                                    text = "${formatTime(currentPositionMs)} / ${formatTime(totalDurationMs)}",
//                                    color = Color.White,
//                                    fontSize = 11.sp
//                                )
//                            }
//
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                IconButton(
//                                    onClick = { showSpeedSheet = true },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.Settings,
//                                        contentDescription = "Settings",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                IconButton(
//                                    onClick = { showSubtitleSheet = true },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.ClosedCaption,
//                                        contentDescription = "Captions",
//                                        tint = if (isSubtitlesEnabled) Color.Red else Color.White
//                                    )
//                                }
//
//                                IconButton(
//                                    onClick = {
//                                        resizeModeState = when (resizeModeState) {
//                                            AspectRatioFrameLayout.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_FILL
//                                            AspectRatioFrameLayout.RESIZE_MODE_FILL -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
//                                            else -> AspectRatioFrameLayout.RESIZE_MODE_FIT
//                                        }
//                                    },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.PictureInPictureAlt,
//                                        contentDescription = "Aspect Ratio",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                IconButton(
//                                    onClick = {
//                                        activity?.requestedOrientation = if (isLandscape) {
//                                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                                        } else {
//                                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                                        }
//                                    },
//                                    modifier = Modifier.size(32.dp)
//                                ) {
//                                    Icon(
//                                        imageVector = if (isLandscape) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
//                                        contentDescription = "Fullscreen Toggle",
//                                        tint = Color.White
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            // SPEED SELECTION SHEET
//            if (showSpeedSheet) {
//                ModalBottomSheet(
//                    onDismissRequest = { showSpeedSheet = false },
//                    sheetState = rememberModalBottomSheetState(),
//                    containerColor = Color(0xFF1E1E1E)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(24.dp)
//                    ) {
//                        Text(
//                            text = "Playback Speed",
//                            color = Color.White,
//                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        val speeds = listOf(0.5f, 1.0f, 1.25f, 1.5f, 2.0f)
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            speeds.forEach { speed ->
//                                TextButton(
//                                    onClick = {
//                                        currentPlaybackSpeed = speed
//                                        exoPlayer.setPlaybackSpeed(speed)
//                                        showSpeedSheet = false
//                                    }
//                                ) {
//                                    Text(
//                                        text = "${speed}x",
//                                        color = if (currentPlaybackSpeed == speed) Color.Red else Color.White
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            // SUBTITLE TRACK SELECTION SHEET
//            if (showSubtitleSheet) {
//                ModalBottomSheet(
//                    onDismissRequest = { showSubtitleSheet = false },
//                    sheetState = rememberModalBottomSheetState(),
//                    containerColor = Color(0xFF1E1E1E)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(24.dp)
//                    ) {
//                        Text(
//                            text = "Captions / Subtitles",
//                            color = Color.White,
//                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        TextButton(
//                            onClick = {
//                                isSubtitlesEnabled = false
//                                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
//                                    .buildUpon()
//                                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
//                                    .build()
//                                showSubtitleSheet = false
//                            },
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.Start
//                            ) {
//                                Text(
//                                    text = "Off",
//                                    color = if (!isSubtitlesEnabled) Color.Red else Color.White
//                                )
//                            }
//                        }
//
//                        currentTracks?.groups?.filter { it.type == C.TRACK_TYPE_TEXT }?.forEach { group ->
//                            for (i in 0 until group.length) {
//                                val format = group.getTrackFormat(i)
//                                val label = format.label ?: format.language ?: "Subtitle ${i + 1}"
//                                val isSelected = group.isTrackSelected(i) && isSubtitlesEnabled
//
//                                TextButton(
//                                    onClick = {
//                                        isSubtitlesEnabled = true
//                                        exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
//                                            .buildUpon()
//                                            .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
//                                            .setOverrideForType(
//                                                TrackSelectionOverride(group.mediaTrackGroup, i)
//                                            )
//                                            .build()
//                                        showSubtitleSheet = false
//                                    },
//                                    modifier = Modifier.fillMaxWidth()
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        horizontalArrangement = Arrangement.Start
//                                    ) {
//                                        Text(
//                                            text = label,
//                                            color = if (isSelected) Color.Red else Color.White
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// Helper to format Milliseconds into HH:MM:SS or MM:SS
//private fun formatTime(timeMs: Long): String {
//    val totalSeconds = timeMs / 1000
//    val seconds = totalSeconds % 60
//    val minutes = (totalSeconds / 60) % 60
//    val hours = totalSeconds / 3600
//
//    return if (hours > 0) {
//        String.format("%02d:%02d:%02d", hours, minutes, seconds)
//    } else {
//        String.format("%02d:%02d", minutes, seconds)
//    }
//}}

package com.example.alphaplayer.ui.screens.player

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPictureAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

// Helper function: M3U Pipe Format URL & Headers Parsing Logic
private fun parseM3uUrl(rawUrl: String): Pair<String, Map<String, String>> {
    val headers = mutableMapOf<String, String>()
    if (rawUrl.contains("|")) {
        val parts = rawUrl.split("|")
        val cleanUrl = parts[0].trim()

        for (i in 1 until parts.size) {
            val headerPair = parts[i].split("=")
            if (headerPair.size == 2) {
                headers[headerPair[0].trim()] = headerPair[1].trim()
            }
        }
        return Pair(cleanUrl, headers)
    }
    return Pair(rawUrl.trim(), headers)
}

private fun openInBrowser(context: Context, url: String) {
    try {
        val cleanUrl = if (url.contains("|")) url.substringBefore("|") else url
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cleanUrl))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Cannot open URL", Toast.LENGTH_SHORT).show()
    }
}

private fun formatTime(millis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    title: String,
    url: String,
    navController: NavController,
    headersJson: String? = null
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val lifecycleOwner = LocalLifecycleOwner.current

    val passedHeaders = remember(headersJson) {
        if (headersJson.isNullOrEmpty()) null
        else try {
            kotlinx.serialization.json.Json.decodeFromString<Map<String, String>>(headersJson)
        } catch (e: Exception) {
            null
        }
    }

    LaunchedEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    var isBarsVisible by remember { mutableStateOf(false) }
    var isPlayingState by remember { mutableStateOf(true) }
    var showSpeedSheet by remember { mutableStateOf(false) }
    var showSubtitleSheet by remember { mutableStateOf(false) }
    var currentPlaybackSpeed by remember { mutableFloatStateOf(1.0f) }

    var currentTracks by remember { mutableStateOf<Tracks?>(null) }
    var isSubtitlesEnabled by remember { mutableStateOf(true) }

    var savedPlaybackPositionMs by rememberSaveable { mutableLongStateOf(0L) }
    var currentPositionMs by remember { mutableLongStateOf(savedPlaybackPositionMs) }
    var totalDurationMs by remember { mutableLongStateOf(0L) }

    var resizeModeState by rememberSaveable {
        mutableIntStateOf(if (isLandscape) AspectRatioFrameLayout.RESIZE_MODE_FILL else AspectRatioFrameLayout.RESIZE_MODE_FIT)
    }

    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val maxVolume = remember { audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat() }
    var currentVolume by remember { mutableFloatStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()) }
    var isMuted by remember { mutableStateOf(false) }
    var currentBrightness by remember {
        mutableFloatStateOf(
            activity?.window?.attributes?.screenBrightness.takeIf { it != null && it >= 0 } ?: 0.5f
        )
    }

    val loadControl = remember {
        DefaultLoadControl.Builder()
            .setBufferDurationsMs(10000, 50000, 1500, 3000)
            .build()
    }

    val exoPlayer = remember(url, passedHeaders) {
        // Parse Pipe Separated M3U URL + Headers
        val (cleanUrl, parsedPipeHeaders) = parseM3uUrl(url)

        val httpDataSourceFactory = DefaultHttpDataSource.Factory().apply {
            val userAgent = parsedPipeHeaders["User-Agent"]
                ?: passedHeaders?.get("User-Agent")
                ?: passedHeaders?.get("user-agent")
                ?: "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"

            setUserAgent(userAgent)
            setAllowCrossProtocolRedirects(true)

            val requestProperties = mutableMapOf<String, String>()

            requestProperties["Accept"] = "*/*"
            requestProperties["Accept-Language"] = "en-US,en;q=0.9"
            requestProperties["Connection"] = "keep-alive"

            // Exact referer detection
            val refererVal = parsedPipeHeaders["Referer"]
                ?: passedHeaders?.get("Referer")
                ?: passedHeaders?.get("referer")
                ?: when {
                    cleanUrl.contains("fibwatch") -> "https://fibwatch.art/"
                    cleanUrl.contains("b-cdn.net") -> "https://fibwatch.art/"
                    else -> "https://google.com/"
                }

            requestProperties["Referer"] = refererVal
            requestProperties["Origin"] = refererVal.trimEnd('/')

            // Merge passed headers and pipe headers
            passedHeaders?.forEach { (k, v) -> requestProperties[k] = v }
            parsedPipeHeaders.forEach { (k, v) -> requestProperties[k] = v }

            setDefaultRequestProperties(requestProperties)
        }

        // Determine MediaSource type (MKV / Progressive vs HLS M3U8)
        val mediaItemBuilder = MediaItem.Builder().setUri(cleanUrl)
        val mediaSource = if (cleanUrl.endsWith(".mkv", ignoreCase = true) || cleanUrl.endsWith(".mp4", ignoreCase = true)) {
            ProgressiveMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(mediaItemBuilder.build())
        } else {
            if (cleanUrl.contains(".m3u8", ignoreCase = true)) {
                mediaItemBuilder.setMimeType(MimeTypes.APPLICATION_M3U8)
            }
            DefaultMediaSourceFactory(context)
                .setDataSourceFactory(httpDataSourceFactory)
                .createMediaSource(mediaItemBuilder.build())
        }

        ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()
            .apply {
                setMediaSource(mediaSource)

                if (savedPlaybackPositionMs > 0L) {
                    seekTo(savedPlaybackPositionMs)
                }

                repeatMode = Player.REPEAT_MODE_OFF
                playWhenReady = true
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                prepare()

                addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        error.printStackTrace()
                        Toast.makeText(context, "Playback Error: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)
                        isPlayingState = isPlaying
                        isBarsVisible = !isPlaying
                    }

                    override fun onTracksChanged(tracks: Tracks) {
                        super.onTracksChanged(tracks)
                        currentTracks = tracks
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        if (playbackState == Player.STATE_READY) {
                            totalDurationMs = duration.coerceAtLeast(0L)
                        }
                    }
                })
            }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_PAUSE) {
                exoPlayer.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(isPlayingState) {
        while (isPlayingState) {
            currentPositionMs = exoPlayer.currentPosition.coerceAtLeast(0L)
            savedPlaybackPositionMs = currentPositionMs
            totalDurationMs = exoPlayer.duration.coerceAtLeast(0L)
            delay(500)
        }
    }

    DisposableEffect(isBarsVisible, isLandscape) {
        val window = activity?.window
        val insetsController = window?.let { WindowCompat.getInsetsController(it, it.decorView) }

        insetsController?.apply {
            if (!isBarsVisible || isLandscape) {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            } else {
                show(WindowInsetsCompat.Type.systemBars())
            }
        }

        onDispose {
            insetsController?.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            savedPlaybackPositionMs = exoPlayer.currentPosition
            exoPlayer.release()
        }
    }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = false
                        keepScreenOn = true
                        setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                        resizeMode = resizeModeState

                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                update = { view ->
                    view.resizeMode = resizeModeState
                }
            )

            // Brightness & Volume Gestures
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                val delta = -dragAmount / 500f
                                currentBrightness = min(1f, max(0.01f, currentBrightness + delta))
                                activity?.window?.let { win ->
                                    val layoutParams = win.attributes
                                    layoutParams.screenBrightness = currentBrightness
                                    win.attributes = layoutParams
                                }
                            }
                        }
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                val delta = -dragAmount / 30f
                                currentVolume = min(maxVolume, max(0f, currentVolume + delta))
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    currentVolume.toInt(),
                                    AudioManager.FLAG_SHOW_UI
                                )
                            }
                        }
                )
            }

            // Tap Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                isBarsVisible = !isBarsVisible
                            }
                        )
                    }
            )

            AnimatedVisibility(
                visible = isBarsVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    // TOP BAR
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
                                )
                            )
                    ) {
                        CenterAlignedTopAppBar(
                            modifier = Modifier.statusBarsPadding(),
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color.Transparent,
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            ),
                            title = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { openInBrowser(context, url) }) {
                                    Icon(
                                        imageVector = Icons.Default.Download,
                                        contentDescription = "Download Movie"
                                    )
                                }
                            }
                        )
                    }

                    // CENTER PLAY/PAUSE
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .clickable {
                                if (exoPlayer.isPlaying) {
                                    exoPlayer.pause()
                                } else {
                                    exoPlayer.play()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPlayingState) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // BOTTOM CONTROL BAR
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f))
                                )
                            )
                            .navigationBarsPadding()
                    ) {
                        Slider(
                            value = currentPositionMs.toFloat(),
                            onValueChange = { newPos ->
                                currentPositionMs = newPos.toLong()
                                savedPlaybackPositionMs = currentPositionMs
                                exoPlayer.seekTo(currentPositionMs)
                            },
                            valueRange = 0f..(totalDurationMs.coerceAtLeast(1L)).toFloat(),
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red)
                                )
                            },
                            track = { sliderState ->
                                SliderDefaults.Track(
                                    sliderState = sliderState,
                                    modifier = Modifier.height(2.dp),
                                    colors = SliderDefaults.colors(
                                        activeTrackColor = Color.Red,
                                        inactiveTrackColor = Color.White.copy(alpha = 0.35f)
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { exoPlayer.seekBack() },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SkipPrevious,
                                        contentDescription = "Previous",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        if (exoPlayer.isPlaying) {
                                            exoPlayer.pause()
                                        } else {
                                            exoPlayer.play()
                                        }
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingState) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "Play/Pause",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = { exoPlayer.seekForward() },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SkipNext,
                                        contentDescription = "Next",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        isMuted = !isMuted
                                        exoPlayer.volume = if (isMuted) 0f else 1f
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                        contentDescription = "Volume",
                                        tint = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = "${formatTime(currentPositionMs)} / ${formatTime(totalDurationMs)}",
                                    color = Color.White,
                                    fontSize = 11.sp
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { showSpeedSheet = true },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = { showSubtitleSheet = true },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ClosedCaption,
                                        contentDescription = "Captions",
                                        tint = if (isSubtitlesEnabled) Color.Red else Color.White
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        resizeModeState = when (resizeModeState) {
                                            AspectRatioFrameLayout.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                                            AspectRatioFrameLayout.RESIZE_MODE_FILL -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                                            else -> AspectRatioFrameLayout.RESIZE_MODE_FIT
                                        }
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PictureInPictureAlt,
                                        contentDescription = "Aspect Ratio",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        activity?.requestedOrientation = if (isLandscape) {
                                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                        } else {
                                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                        }
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isLandscape) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                                        contentDescription = "Fullscreen Toggle",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // SPEED SELECTION SHEET
            if (showSpeedSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSpeedSheet = false },
                    sheetState = rememberModalBottomSheetState(),
                    containerColor = Color(0xFF1E1E1E)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Playback Speed",
                            color = Color.White,
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val speeds = listOf(0.5f, 1.0f, 1.25f, 1.5f, 2.0f)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            speeds.forEach { speed ->
                                TextButton(
                                    onClick = {
                                        currentPlaybackSpeed = speed
                                        exoPlayer.setPlaybackSpeed(speed)
                                        showSpeedSheet = false
                                    }
                                ) {
                                    Text(
                                        text = "${speed}x",
                                        color = if (currentPlaybackSpeed == speed) Color.Red else Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // SUBTITLE TRACK SELECTION SHEET
            if (showSubtitleSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSubtitleSheet = false },
                    sheetState = rememberModalBottomSheetState(),
                    containerColor = Color(0xFF1E1E1E)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Captions / Subtitles",
                            color = Color.White,
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextButton(
                            onClick = {
                                isSubtitlesEnabled = !isSubtitlesEnabled
                                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                                    .buildUpon()
                                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, !isSubtitlesEnabled)
                                    .build()
                                showSubtitleSheet = false
                            }
                        ) {
                            Text(
                                text = if (isSubtitlesEnabled) "Disable Captions" else "Enable Captions",
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}