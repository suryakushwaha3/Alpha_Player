package com.example.alphaplayer.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeContent(
    padding: PaddingValues
) {

    val categories = listOf(
        "🔥 Trending Now",
        "🎬 Popular Movies",
        "⭐ Top Rated",
        "💥 Action",
        "😂 Comedy",
        "👻 Horror",
        "❤️ Romantic",
        "🎞 Upcoming"
    )

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        contentPadding = PaddingValues(

            top = padding.calculateTopPadding(),

            bottom = padding.calculateBottomPadding(),

            start = 16.dp,

            end = 16.dp

        ),

        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {

        item {

            Spacer(modifier = Modifier.height(8.dp))

            // BannerSlider()
        }

        items(categories) { title ->

            Column {

                Text(

                    text = title,

                    color = Color.White,

                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),

                    colors = CardDefaults.cardColors(

                        containerColor = Color(0xFF1A1A1A)

                    ),

                    shape = RoundedCornerShape(20.dp)

                ) {

                    Box(

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)

                    ) {

                        Text(

                            text = "Movie Posters will come here from API",

                            color = Color.Gray

                        )

                    }

                }

            }

        }

    }

}