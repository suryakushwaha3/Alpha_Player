
package com.example.alphaplayer.ui.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.alphaplayer.data.manager.WatchlistManager
import com.example.alphaplayer.data.model.M3UItem
import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// -----------------------------------------------------------------------------
// 1. Shimmer Effect Animation Modifier
// -----------------------------------------------------------------------------
fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerFloat"
    )

    val shimmerColors = listOf(
        Color(0xFF1E1E24),
        Color(0xFF2D2D38),
        Color(0xFF1E1E24)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 200f, translateAnim - 200f),
        end = Offset(translateAnim, translateAnim)
    )

    background(brush)
}

// -----------------------------------------------------------------------------
// 2. Dynamic Shimmer Grid Placeholder (Loading ke waqt ke liye)
// -----------------------------------------------------------------------------
@Composable
fun HomeShimmerLoading() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(6) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(255.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF14141A))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Movie Poster Skeleton with Shimmer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .shimmerEffect()
                    )

                    // Title Bar Area with Shimmer Text Placeholders
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .background(Color(0xFF171717))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. HomeContent Screen
// -----------------------------------------------------------------------------
@Composable
fun HomeContent(
    padding: PaddingValues,
    playlistUrl: String,
    navController: NavController,
    searchText: String
) {
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val movies by viewModel.movies.collectAsState()
    val scope = rememberCoroutineScope()

    // Playlist URL change hone par load trigger karein
    LaunchedEffect(playlistUrl) {
        if (playlistUrl.isNotBlank()) {
            viewModel.loadPlaylist(playlistUrl)
        }
    }

    val filteredMovies = remember(movies, searchText) {
        if (searchText.isBlank()) {
            movies
        } else {
            movies.filter {
                it.title.contains(searchText, ignoreCase = true) ||
                        (it.group ?: "").contains(searchText, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )
    ) {
        when {
            // Loading State - Premium Animated Shimmer Effect
            uiState is HomeUiState.Loading && movies.isEmpty() -> {
                HomeShimmerLoading()
            }

            // Error or Empty State
            movies.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchText.isNotBlank()) "No movies match your search" else "No Data Available",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Content Loaded State
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(
                        items = filteredMovies,
                        key = { index, item -> "${item.url}_${item.title}_$index" }
                    ) { _, movie ->
                        MovieItem(
                            movie = movie,
                            onMovieClick = {
                                // Watchlist me add karein
                                WatchlistManager.addToWatchlist(movie)

                                scope.launch {
                                    delay(100)
                                    val headersJsonString = if (movie.headers.isEmpty()) {
                                        null
                                    } else {
                                        try {
                                            Json.encodeToString(movie.headers)
                                        } catch (e: Exception) {
                                            null
                                        }
                                    }

                                    navController.navigate(
                                        BottomNavRoutes.Player(
                                            title = movie.title,
                                            url = movie.url,
                                            headersJson = headersJsonString
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. MovieItem Component
// -----------------------------------------------------------------------------
@Composable
fun MovieItem(
    movie: M3UItem,
    onMovieClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    var selected by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        label = "scale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFE50914) else Color.Transparent,
        label = "border"
    )

    val elevation by animateFloatAsState(
        targetValue = if (selected) 8f else 2f,
        label = "elevation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(255.dp)
            .scale(scale)
            .graphicsLayer {
                shadowElevation = elevation.dp.toPx()
            }
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                selected = true
                onMovieClick()
            },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF1F1414) else Color(0xFF171717)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.dp
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current

            // Clean Logo URL
            val finalLogoUrl = remember(movie.logo) {
                movie.logo?.let { rawLogo ->
                    if (rawLogo.contains("hf.space/image?url=")) {
                        rawLogo.substringAfter("hf.space/image?url=").trim()
                    } else {
                        rawLogo.trim()
                    }
                }
            }

            // Dynamic Referer Resolution
            val imageReferer = remember(movie, finalLogoUrl) {
                when {
                    movie.headers.containsKey("Referer") -> movie.headers["Referer"]!!
                    finalLogoUrl?.contains("github") == true -> "https://github.com/"
                    else -> {
                        try {
                            val uri = java.net.URI(finalLogoUrl ?: movie.url)
                            "${uri.scheme}://${uri.host}/"
                        } catch (e: Exception) {
                            "https://fibwatch.art/"
                        }
                    }
                }
            }

            // Image Request
            val imageRequest = remember(finalLogoUrl, imageReferer) {
                ImageRequest.Builder(context)
                    .data(finalLogoUrl)
                    .addHeader("User-Agent", movie.headers["User-Agent"] ?: "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .addHeader("Referer", imageReferer)
                    .listener(
                        onError = { _, result ->
                            android.util.Log.e("AlphaPlayerImage", "Failed to load image: $finalLogoUrl, Error: ${result.throwable.message}")
                        }
                    )
                    .crossfade(true)
                    .build()
            }

            // AsyncImage with Shimmer Background Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                    .shimmerEffect()
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = movie.title,
                    error = painterResource(id = android.R.drawable.ic_dialog_alert),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(Color(0xFF171717)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}