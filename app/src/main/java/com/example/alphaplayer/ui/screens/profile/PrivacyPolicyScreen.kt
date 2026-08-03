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
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    navController: NavHostController
) {
    Scaffold(
        containerColor = Color(0xFF0B0B0E), // Deep rich black-blue tone for premium look
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
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

            // 🌟 Professional Security Header Icon with Glow effect
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
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = Color(0xFFE50914),
                    modifier = Modifier.size(45.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Privacy & Security",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Last Updated Badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF1C1C24),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2D2D3A))
            ) {
                Text(
                    text = "Last updated: July 2026",
                    color = Color(0xFFA0A0AB),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 🌟 Professional Policy Cards Section
            PolicySectionCard(
                title = "1. Information We Collect",
                text = "We collect basic account information such as your name, email address, and profile details to provide our services."
            )

            PolicySectionCard(
                title = "2. How We Use Your Information",
                text = "Your information is used to manage your account, improve app performance, personalize content, and enhance user experience."
            )

            PolicySectionCard(
                title = "3. Data Security",
                text = "We use secure Firebase services to protect your personal information from unauthorized access."
            )

            PolicySectionCard(
                title = "4. Third-Party Services",
                text = "Our application may use Firebase Authentication, Firestore Database, and other Google services."
            )

            PolicySectionCard(
                title = "5. Your Rights",
                text = "You may update or delete your profile information at any time through the application."
            )

            PolicySectionCard(
                title = "6. Contact Us",
                text = "For any privacy-related questions, contact us at:\n\nalphaplayer@gmail.com"
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// 🌟 Reusable Professional Card Component for Policy Sections
@Composable
fun PolicySectionCard(
    title: String,
    text: String
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
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
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

// Keeping original placeholders safe to prevent any compatibility/compile issues
@Composable
fun PolicyTitle(text: String) {
    Text(text = text, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun PolicyText(text: String) {
    Text(text = text, color = Color.LightGray, fontSize = 15.sp, lineHeight = 24.sp)
    Spacer(modifier = Modifier.height(20.dp))
}