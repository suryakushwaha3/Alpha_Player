package com.example.alphaplayer.Components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

data class BannerItem(
    val image: String,
    val title: String,
    val description: String
)

@Composable
fun BannerSlider(

    banners: List<BannerItem>,

    onPlayClick: (BannerItem) -> Unit = {},

    onBannerClick: (BannerItem) -> Unit = {}

) {

    val pagerState = rememberPagerState(
        pageCount = { banners.size }
    )

    LaunchedEffect(Unit) {

        while (true) {

            delay(4000)

            val nextPage =
                (pagerState.currentPage + 1) % banners.size

            pagerState.animateScrollToPage(nextPage)

        }

    }

    Column {

        HorizontalPager(

            state = pagerState,

            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)

        ) { page ->

            val movie = banners[page]

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onBannerClick(movie)
                    },

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                )

            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Image(

                        painter = rememberAsyncImagePainter(movie.image),

                        contentDescription = movie.title,

                        modifier = Modifier.fillMaxSize(),

                        contentScale = ContentScale.Crop

                    )

                    Box(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(

                                Brush.verticalGradient(

                                    colors = listOf(

                                        Color.Transparent,

                                        Color.Black.copy(alpha = 0.15f),

                                        Color.Black.copy(alpha = 0.85f),

                                        Color.Black

                                    )

                                )

                            )

                    )

                    Column(

                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)

                    ) {

                        Text(

                            text = movie.title,

                            color = Color.White,

                            fontSize = 28.sp,

                            fontWeight = FontWeight.ExtraBold

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(

                            text = movie.description,

                            color = Color.LightGray,

                            fontSize = 14.sp,

                            maxLines = 2

                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row {

                            Button(

                                onClick = {

                                    onPlayClick(movie)

                                },

                                colors = ButtonDefaults.buttonColors(

                                    containerColor = Color.Red

                                )

                            ) {

                                Icon(

                                    imageVector = Icons.Default.PlayArrow,

                                    contentDescription = null

                                )

                                Spacer(
                                    modifier = Modifier.width(4.dp)
                                )

                                Text("Play")

                            }

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

                            Button(

                                onClick = { },

                                colors = ButtonDefaults.buttonColors(

                                    containerColor = Color.DarkGray

                                )

                            ) {

                                Icon(

                                    imageVector = Icons.Default.Add,

                                    contentDescription = null

                                )

                                Spacer(
                                    modifier = Modifier.width(4.dp)
                                )

                                Text("My List")

                            }

                        }

                    }

                }

            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.Center

        ) {

            repeat(banners.size) { index ->

                Box(

                    modifier = Modifier
                        .padding(4.dp)
                        .size(
                            if (pagerState.currentPage == index) 10.dp
                            else 8.dp
                        )
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (pagerState.currentPage == index)
                                Color.Red
                            else
                                Color.Gray
                        )

                )

            }

        }

    }

}