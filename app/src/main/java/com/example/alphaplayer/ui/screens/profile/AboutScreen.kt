package com.example.alphaplayer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(

    navController: NavHostController

) {

    Scaffold(

        containerColor = Color.Black,

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text = "About App",

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

                            imageVector = Icons.Default.ArrowBack,

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

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)

        ) {

            Icon(

                imageVector = Icons.Default.Info,

                contentDescription = null,

                tint = Color(0xFFE50914),

                modifier = Modifier.size(70.dp)

            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = "Alpha Player",

                color = Color.White,

                fontSize = 30.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "Version 1.0.0",

                color = Color.Gray,

                fontSize = 15.sp

            )

            Spacer(modifier = Modifier.height(30.dp))

            AboutTitle("About")

            AboutText(
                "Alpha Player is a modern movie streaming application built using Jetpack Compose. It provides a clean, fast, and user-friendly experience for browsing movies, managing your watchlist, downloading content, and editing your profile."
            )

            AboutTitle("Features")

            AboutText(
                "• User Authentication\n" +
                        "• Movie Categories\n" +
                        "• Watchlist\n" +
                        "• Downloads\n" +
                        "• Profile Management\n" +
                        "• Responsive UI\n" +
                        "• Dark Theme"
            )

            AboutTitle("Built With")

            AboutText(
                "• Kotlin\n" +
                        "• Jetpack Compose\n" +
                        "• Firebase Authentication\n" +
                        "• Firebase Firestore\n" +
                        "• Navigation Compose\n" +
                        "• Material 3"
            )

            AboutTitle("Developer")

            AboutText(
                "Developed with ❤️ by Alpha Player Team."
            )

            AboutTitle("Contact")

            AboutText(
                "alphaplayer@gmail.com"
            )

            Spacer(modifier = Modifier.height(30.dp))

        }

    }

}

@Composable
fun AboutTitle(

    text: String

) {

    Text(

        text = text,

        color = Color.White,

        fontSize = 20.sp,

        fontWeight = FontWeight.Bold

    )

    Spacer(modifier = Modifier.height(8.dp))

}

@Composable
fun AboutText(

    text: String

) {

    Text(

        text = text,

        color = Color.LightGray,

        fontSize = 15.sp,

        lineHeight = 22.sp

    )

    Spacer(modifier = Modifier.height(20.dp))

}