package com.example.alphaplayer.MainScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MoviesScreen() {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        contentAlignment = Alignment.Center

    ) {

        Text(

            text = "Movies Screen",

            color = Color.White,

            fontSize = 28.sp

        )

    }

}