package com.example.alphaplayer.ui.screens.auth

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.R
import com.example.alphaplayer.data.repository.M3URepository
import com.example.alphaplayer.ui.navigation.MyNavRoutes
import com.example.alphaplayer.viewmodel.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds

// Helper Composable to keep UI responsive & auto-scaled on every phone screen
@Composable
fun SplashAutoScaledBox(
    targetDesignWidthDp: Float = 390f,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val currentWidthDp = maxWidth.value
        if (currentWidthDp > 0) {
            val scaleFactor = currentWidthDp / targetDesignWidthDp
            val currentDensity = LocalDensity.current

            val customDensity = Density(
                density = currentDensity.density * scaleFactor,
                fontScale = currentDensity.fontScale
            )

            CompositionLocalProvider(LocalDensity provides customDensity) {
                content()
            }
        } else {
            content()
        }
    }
}

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = viewModel()
    val repository = M3URepository()

    // Fixed Status Bar Icons visibility for Dark Cyber Background
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    LaunchedEffect(Unit) {
        // Maximum wait — background parallel loading
        withTimeoutOrNull(10000L.milliseconds) {
            try {
                val firestore = FirebaseFirestore.getInstance()

                coroutineScope {
                    val playlistTask = async(Dispatchers.IO) {
                        firestore.collection("settings").document("playlist").get().await()
                    }
                    val moviesTask = async(Dispatchers.IO) {
                        firestore.collection("settings").document("movies").get().await()
                    }

                    val playlistDocument = playlistTask.await()
                    val moviesDocument = moviesTask.await()

                    val homePlaylistUrl = playlistDocument.getString("playlistUrl")
                    val moviesPlaylistUrl = moviesDocument.getString("url")

                    val jobs = mutableListOf<kotlinx.coroutines.Deferred<Unit>>()

                    if (!homePlaylistUrl.isNullOrEmpty()) {
                        jobs.add(async(Dispatchers.IO) { repository.preloadPlaylist(homePlaylistUrl) })
                    }
                    if (!moviesPlaylistUrl.isNullOrEmpty()) {
                        jobs.add(async(Dispatchers.IO) { repository.preloadPlaylist(moviesPlaylistUrl) })
                    }

                    jobs.forEach { it.await() }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fast Navigation
        val targetRoute = if (authViewModel.isUserLoggedIn()) {
            MyNavRoutes.HomeScreen
        } else {
            MyNavRoutes.LoginScreen
        }

        navController.navigate(targetRoute) {
            popUpTo(MyNavRoutes.SplashScreen) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    // Multi-Layer Dynamic Background Pulse Animation
    val infiniteTransition = rememberInfiniteTransition(label = "EliteBackgroundPulse")
    val scaleAnim by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbScale"
    )

    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbAlpha"
    )

    // Deep Cyber-Purple / Obsidian Gradient Theme
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF030108), // Almost Pitch Black Violet
            Color(0xFF0A0314), // Ultra Dark Deep Purple
            Color(0xFF17082B)  // Rich Dark Magenta-Violet Shade
        )
    )

    SplashAutoScaledBox {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            // Dynamic Glowing Neon Orb for Depth
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .scale(scaleAnim)
                    .blur(140.dp)
                    .background(Color(0xFFC026D3).copy(alpha = alphaAnim), CircleShape)
            )

            // Smooth Logo & Content Entrance Animation
            var visibleState by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                visibleState = true
            }

            AnimatedVisibility(
                visible = visibleState,
                enter = fadeIn(animationSpec = tween(700)) +
                        scaleIn(initialScale = 0.85f, animationSpec = tween(700, easing = FastOutSlowInEasing)),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Adjusted Logo Container with Balanced RoundedCornerShape and Glow
                    Box(
                        modifier = Modifier
                            .size(105.dp)
                            .shadow(20.dp, RoundedCornerShape(26.dp), spotColor = Color(0xFFC026D3))
                            .border(1.dp, Color(0xFFD946EF).copy(alpha = 0.5f), RoundedCornerShape(26.dp))
                            .background(Color(0xFF130624), RoundedCornerShape(26.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alpha),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(78.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Alpha Player",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Movies • Web Series • Entertainment",
                        fontSize = 13.sp,
                        color = Color(0xFFD8B4FE),
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}