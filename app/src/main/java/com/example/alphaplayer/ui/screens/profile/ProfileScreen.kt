package com.example.alphaplayer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.data.model.UserModel
import com.example.alphaplayer.ui.components.ProfileMenuItem
import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes
import com.example.alphaplayer.ui.navigation.MyNavRoutes
import com.example.alphaplayer.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    bottomNavController: NavHostController? = null,
    authViewModel: AuthViewModel = viewModel()
) {
    var user by remember { mutableStateOf(UserModel()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(navController.currentBackStackEntry) {
        authViewModel.getCurrentUser { currentUser ->
            currentUser?.let {
                user = it
            }
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0E)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFFE50914)
            )
        }
        return
    }

    Scaffold(
        containerColor = Color(0xFF0B0B0E) // Premium deep dark theme
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0E))
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // 🌟 Professional Gradient Header with Glow Effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE50914).copy(alpha = 0.4f),
                                Color(0xFF14141A).copy(alpha = 0.8f),
                                Color(0xFF0B0B0E)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    // Glowing Avatar Border
                    Box(
                        modifier = Modifier
                            .size(116.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFFE50914).copy(alpha = 0.5f), Color.Transparent)
                                )
                            )
                            .border(2.dp, Color(0xFFE50914).copy(alpha = 0.7f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier.size(102.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1C1C24)
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user.fullName.firstOrNull()?.uppercase() ?: "A",
                                    color = Color.White,
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = user.fullName,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = Color(0xFF00E5FF),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = user.email,
                        color = Color(0xFFA0A0AB),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🌟 Modern Menu Items Section (Wrapped inside stylish spacing container)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ProfileMenuItem(
                    title = "Edit Profile",
                    icon = "✏️",
                    onClick = {
                        navController.navigate(MyNavRoutes.EditProfileScreen)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    title = "My Watchlist",
                    icon = "❤️",
                    onClick = {
                        bottomNavController?.navigate(BottomNavRoutes.Watchlist) {
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    title = "Change Password",
                    icon = "🔒",
                    onClick = {
                        navController.navigate(MyNavRoutes.ChangePasswordScreen)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    title = "Downloads",
                    icon = "⬇️",
                    onClick = {
                        navController.navigate(MyNavRoutes.DownloadScreen)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    title = "Privacy Policy",
                    icon = "📄",
                    onClick = {
                        navController.navigate(MyNavRoutes.PrivacyPolicyScreen)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    title = "About App",
                    icon = "ℹ️",
                    onClick = {
                        navController.navigate(MyNavRoutes.AboutScreen)
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 🌟 Professional Modern Logout Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE50914)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                onClick = {
                    authViewModel.logout()
                    navController.navigate(MyNavRoutes.LoginScreen) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Logout",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color(0xFF22222D),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(25.dp))

            // App Footer Info
            Text(
                text = "Alpha Player",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Version 1.0.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFFA0A0AB),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Made with ❤️ in India",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFFA0A0AB),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}