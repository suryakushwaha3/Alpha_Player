package com.example.alphaplayer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
        containerColor = Color(0xFF0B0B0E), // Deep rich black-blue tone for premium look
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About App",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0E))
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 🌟 Professional App Logo/Header Card with Gradient Accent
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFFE50914).copy(alpha = 0.3f), Color.Transparent)
                        )
                    )
                    .border(1.5.dp, Color(0xFFE50914).copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFFE50914),
                    modifier = Modifier.size(45.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Alpha Player",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Version Badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF1C1C24),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2D2D3A))
            ) {
                Text(
                    text = "Version 1.0.0 (Stable)",
                    color = Color(0xFFA0A0AB),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 🌟 Modern Cards Sections
            AboutSectionCard(
                title = "About",
                text = "Alpha Player is a modern movie streaming application built using Jetpack Compose. It provides a clean, fast, and user-friendly experience for browsing movies, managing your watchlist, downloading content, and editing your profile.",
                icon = Icons.Default.Star
            )

            AboutSectionCard(
                title = "Features",
                text = "• User Authentication\n• Movie Categories\n• Watchlist\n• Downloads\n• Profile Management\n• Responsive UI\n• Dark Theme",
                icon = Icons.Default.Security
            )

            AboutSectionCard(
                title = "Built With",
                text = "• Kotlin\n• Jetpack Compose\n• Firebase Authentication\n• Firebase Firestore\n• Navigation Compose\n• Material 3",
                icon = Icons.Default.Code
            )

            AboutSectionCard(
                title = "Developer & Contact",
                text = "Developed with ❤️ by Alpha Player Team.\n\n📧 alphaplayer@gmail.com",
                icon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// 🌟 Reusable Professional Card Component for each section
@Composable
fun AboutSectionCard(
    title: String,
    text: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF14141A)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF22222D)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFE50914),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = text,
                color = Color(0xFFB3B3C1),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}

// Keeping original placeholders safe to prevent any unexpected compatibility issues
@Composable
fun AboutTitle(text: String) {
    Text(text = text, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun AboutText(text: String) {
    Text(text = text, color = Color.LightGray, fontSize = 15.sp, lineHeight = 22.sp)
    Spacer(modifier = Modifier.height(20.dp))
}