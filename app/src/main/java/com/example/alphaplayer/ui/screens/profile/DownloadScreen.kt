package com.example.alphaplayer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(

    navController: NavHostController

) {

    val downloads = listOf(

        DownloadItem(
            "Avengers: Endgame",
            "Movie",
            100
        ),

        DownloadItem(
            "Money Heist S01E01",
            "Series",
            78
        ),

        DownloadItem(
            "Stranger Things",
            "Series",
            45
        )

    )

    Scaffold(

        containerColor = Color.Black,

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        "Downloads",

                        color = Color.White

                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            navController.popBackStack()

                        }

                    ) {

                        Icon(

                            Icons.Default.ArrowBack,

                            contentDescription = null,

                            tint = Color.White

                        )

                    }

                },

                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = Color.Black

                )

            )

        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding),

            contentPadding = PaddingValues(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            items(downloads) { item ->

                Card(

                    colors = CardDefaults.cardColors(

                        containerColor = Color(0xFF1A1A1A)

                    )

                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(

                            Icons.Default.DownloadDone,

                            contentDescription = null,

                            tint = Color(0xFFE50914),

                            modifier = Modifier.size(48.dp)

                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(

                            modifier = Modifier.weight(1f)

                        ) {

                            Text(

                                text = item.title,

                                color = Color.White,

                                fontWeight = FontWeight.Bold,

                                fontSize = 18.sp

                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(

                                text = item.type,

                                color = Color.Gray,

                                fontSize = 14.sp

                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            LinearProgressIndicator(

                                progress = { item.progress / 100f },

                                modifier = Modifier.fillMaxWidth(),

                                color = Color(0xFFE50914),

                                trackColor = Color.DarkGray

                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(

                                "${item.progress}% Downloaded",

                                color = Color.LightGray,

                                fontSize = 12.sp

                            )

                        }

                        IconButton(

                            onClick = {

                            }

                        ) {

                            Icon(

                                Icons.Default.Delete,

                                contentDescription = null,

                                tint = Color.Red

                            )

                        }

                    }

                }

            }

        }

    }

}

data class DownloadItem(

    val title: String,

    val type: String,

    val progress: Int

)