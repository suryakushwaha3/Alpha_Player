package com.example.alphaplayer.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = viewModel()
    val repository = M3URepository()

    LaunchedEffect(Unit) {
        // Maximum 3 second wait — background parallel loading
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.alpha),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(24.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Alpha Player",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Movies • Web Series • Entertainment",
            fontSize = 16.sp,
            color = Color.LightGray
        )
    }
}