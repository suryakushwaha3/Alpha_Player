
package com.example.alphaplayer.ui.screens.movies

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.alphaplayer.data.model.M3UItem
import com.example.alphaplayer.data.repository.M3URepository
import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MoviesScreen(
    navController: NavHostController,
    padding: PaddingValues = PaddingValues(0.dp),
    searchText: String = ""
) {
    var movieList by remember { mutableStateOf<List<M3UItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    val repository = remember { M3URepository() }

    val filteredMovies = remember(movieList, searchText) {
        val query = searchText.trim().lowercase()
        movieList.filter { movie ->
            val cleanTitle = movie.title.replace("\r", "").lowercase()
            val cleanGroup = movie.group?.replace("\r", "")?.lowercase() ?: ""
            query.isEmpty() || cleanTitle.contains(query) || cleanGroup.contains(query)
        }
    }

    LaunchedEffect(Unit) {
        try {
            val document = FirebaseFirestore.getInstance()
                .collection("settings")
                .document("movies")
                .get()
                .await()

            val movieUrl = document.getString("url")
            if (!movieUrl.isNullOrEmpty()) {
                movieList = repository.loadPlaylistWithCache(movieUrl)
            } else {
                error = "Movie URL not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error = e.message ?: "Unknown Error"
        }
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.Black)
    ) {
        Crossfade(
            targetState = isLoading,
            animationSpec = tween(durationMillis = 500),
            label = "loadingCrossfade"
        ) { loading ->
            if (loading) {
                MoviesShimmerGrid()
            } else {
                MoviesGridContent(
                    filteredMovies = filteredMovies,
                    navController = navController,
                    searchText = searchText
                )
            }
        }

        if (error.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error, color = Color.Red, modifier = Modifier.padding(20.dp))
            }
        }
    }
}

@Composable
private fun MoviesGridContent(
    filteredMovies: List<M3UItem>,
    navController: NavHostController,
    searchText: String
) {
    if (filteredMovies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (searchText.isEmpty()) "No movies available" else "No movies found for '$searchText'",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 80.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(
                items = filteredMovies,
                key = { index, _ -> index }
            ) { _, movie ->
                MovieGridItem(
                    movie = movie,
                    onClick = {
                        navController.navigate(
                            BottomNavRoutes.Player(
                                title = movie.title,
                                url = movie.url,
                                headersJson = if (movie.headers.isEmpty()) null else Json.encodeToString<Map<String, String>>(movie.headers)
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MovieGridItem(
    movie: M3UItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            // ✅ Image load na hone के कारणों को ट्रैक करने के लिए Logcat ऐड किया गया है
            AsyncImage(
                model = movie.logo,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)),
                contentScale = ContentScale.Crop,
                onSuccess = { Log.d("IMAGE_LOAD", "Success: ${movie.logo}") },
                onError = { error ->
                    Log.e("IMAGE_LOAD_ERROR", "Failed: ${movie.logo} | Reason: ${error.result.throwable.message}")
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movie.group ?: "Movies",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun MoviesShimmerGrid() {
    val shimmerColors = listOf(
        Color(0xFF2B2B2E),
        Color(0xFF48484E),
        Color(0xFF2B2B2E)
    )

    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnimation"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 300f, translateAnim - 300f),
        end = Offset(translateAnim, translateAnim)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(6) {
            ShimmerMovieItem(brush = brush)
        }
    }
}

@Composable
fun ShimmerMovieItem(brush: Brush) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                    .background(brush)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
            }
        }
    }
}