package com.example.alphaplayer.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var user by remember {

        mutableStateOf(UserModel())

    }

    var isLoading by remember {

        mutableStateOf(true)

    }

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
                .background(Color.Black),

            contentAlignment = Alignment.Center

        ) {

            CircularProgressIndicator(

                color = Color(0xFFE50914)

            )

        }

        return

    }

    Scaffold(

        containerColor = Color.Black

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(padding)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(

                        Brush.verticalGradient(

                            colors = listOf(

                                Color(0xFFE50914),

                                Color(0xFFB20710),

                                Color.Black

                            )

                        )

                    ),

                contentAlignment = Alignment.Center

            ) {

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Card(

                        modifier = Modifier.size(110.dp),

                        shape = CircleShape,

                        colors = CardDefaults.cardColors(

                            containerColor = Color.DarkGray

                        )

                    ) {

                        Box(

                            modifier = Modifier.fillMaxSize(),

                            contentAlignment = Alignment.Center

                        ) {

                            Text(

                                text = user.fullName.firstOrNull()?.uppercase() ?: "A",

                                color = Color.White,

                                fontSize = 44.sp,

                                fontWeight = FontWeight.Bold

                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(

                            text = user.fullName,

                            color = Color.White,

                            fontSize = 28.sp,

                            fontWeight = FontWeight.Bold

                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Icon(

                            imageVector = Icons.Default.Verified,

                            contentDescription = null,

                            tint = Color.Cyan

                        )

                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(

                        text = user.email,

                        color = Color.LightGray,

                        fontSize = 15.sp

                    )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            ProfileMenuItem(

                title = "Edit Profile",

                icon = "✏️",

                onClick = {

                    navController.navigate(

                        MyNavRoutes.EditProfileScreen

                    )

                }

            )

            ProfileMenuItem(

                title = "My Watchlist",

                icon = "❤️",

                onClick = {

                    bottomNavController?.navigate(

                        BottomNavRoutes.Watchlist

                    ) {

                        popUpTo(

                            bottomNavController.graph.startDestinationId

                        ) {

                            saveState = true

                        }

                        launchSingleTop = true

                        restoreState = true

                    }

                }

            )

            ProfileMenuItem(

                title = "Change Password",

                icon = "🔒",

                onClick = {

                    navController.navigate(

                        MyNavRoutes.ChangePasswordScreen

                    )

                }

            )

            ProfileMenuItem(

                title = "Downloads",

                icon = "⬇️",

                onClick = {

                    navController.navigate(

                        MyNavRoutes.DownloadScreen

                    )

                }

            )

            ProfileMenuItem(

                title = "Privacy Policy",

                icon = "📄",

                onClick = {

                    navController.navigate(

                        MyNavRoutes.PrivacyPolicyScreen

                    )

                }

            )

            ProfileMenuItem(

                title = "About App",

                icon = "ℹ️",

                onClick = {

                    navController.navigate(

                        MyNavRoutes.AboutScreen

                    )

                }

            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),

                colors = CardDefaults.cardColors(

                    containerColor = Color(0xFFE50914)

                ),

                onClick = {

                    authViewModel.logout()

                    navController.navigate(

                        MyNavRoutes.LoginScreen

                    ) {

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

                        fontSize = 18.sp,

                        fontWeight = FontWeight.Bold

                    )

                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(

                modifier = Modifier.padding(horizontal = 20.dp),

                color = Color.DarkGray

            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(

                text = "Alpha Player",

                modifier = Modifier.align(Alignment.CenterHorizontally),

                color = Color.White,

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                text = "Version 1.0.0",

                modifier = Modifier.align(Alignment.CenterHorizontally),

                color = Color.Gray,

                fontSize = 14.sp

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "Made with ❤️ in India",

                modifier = Modifier.align(Alignment.CenterHorizontally),

                color = Color.Gray,

                fontSize = 13.sp

            )

            Spacer(modifier = Modifier.height(30.dp))

        }

    }

}