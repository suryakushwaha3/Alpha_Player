package com.example.alphaplayer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// 🌟 Dynamic Download Manager to store and provide downloaded items across the app
object DownloadManager {
    private val _downloads = mutableStateListOf(
        DownloadItem("Avengers: Endgame", "Movie", 100),
        DownloadItem("Money Heist S01E01", "Series", 78),
        DownloadItem("Stranger Things", "Series", 45)
    )

    val downloads: List<DownloadItem> get() = _downloads

    fun addDownload(item: DownloadItem) {
        if (!_downloads.any { it.title == item.title }) {
            _downloads.add(0, item)
        }
    }

    fun removeDownload(item: DownloadItem) {
        _downloads.remove(item)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    navController: NavHostController
) {
    // 🌟 Observing dynamic list state
    val downloadsList = DownloadManager.downloads

    Scaffold(
        containerColor = Color(0xFF0B0B0E), // Deep rich black theme for premium look
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Downloads",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0B0B0E)
                )
            )
        }
    ) { padding ->
        if (downloadsList.isEmpty()) {
            // Empty state when no downloads exist
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0B0B0E))
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.DownloadForOffline,
                        contentDescription = null,
                        tint = Color(0xFF555566),
                        modifier = Modifier.size(70.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No Downloads Yet",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Downloaded movies & series will appear here",
                        color = Color(0xFFA0A0AB),
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0B0B0E))
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(downloadsList, key = { it.title }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF14141A)
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF22222D)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 🌟 Glowing Icon Background
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(Color(0xFFE50914).copy(alpha = 0.3f), Color.Transparent)
                                        )
                                    )
                                    .border(1.dp, Color(0xFFE50914).copy(alpha = 0.5f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DownloadDone,
                                    contentDescription = null,
                                    tint = Color(0xFFE50914),
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.title,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )

                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = item.type,
                                    color = Color(0xFFA0A0AB),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Stylish Progress Bar
                                LinearProgressIndicator(
                                    progress = { item.progress / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = Color(0xFFE50914),
                                    trackColor = Color(0xFF2D2D3A)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = if (item.progress == 100) "Downloaded • Ready to watch" else "${item.progress}% Downloaded",
                                    color = if (item.progress == 100) Color(0xFF00E676) else Color(0xFFA0A0AB),
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Delete / Remove Button
                            IconButton(
                                onClick = {
                                    DownloadManager.removeDownload(item)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Download",
                                    tint = Color(0xFFFF5252),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data class unchanged to maintain compatibility
data class DownloadItem(
    val title: String,
    val type: String,
    val progress: Int
)