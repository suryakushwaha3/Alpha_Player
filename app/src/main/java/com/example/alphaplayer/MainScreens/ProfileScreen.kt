package com.example.alphaplayer.MainScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alphaplayer.Auth.AuthViewModel
import com.example.alphaplayer.Navigation.MyNavRoutes

@Composable
fun ProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()

) {

    val authViewModel: AuthViewModel = viewModel()

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

            //==========================
            // Profile Header
            //==========================

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

                                text = "A",

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

                            text = "Alpha Player",

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

                        text = "alphaplayer@gmail.com",

                        color = Color.LightGray,

                        fontSize = 15.sp

                    )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            // ==========================
            // Paste Part-2 Below Here
            // ==========================

            //==========================
// Profile Menu
//==========================

            ProfileMenuItem(
                title = "Edit Profile",
                icon = "✏️"
            )

            ProfileMenuItem(
                title = "My Watchlist",
                icon = "❤️"
            )

            ProfileMenuItem(
                title = "Change Password",
                icon = "🔒"
            )

            ProfileMenuItem(
                title = "Downloads",
                icon = "⬇️"
            )

            ProfileMenuItem(
                title = "Privacy Policy",
                icon = "📄"
            )

            ProfileMenuItem(
                title = "About App",
                icon = "ℹ️"
            )

            Spacer(modifier = Modifier.height(25.dp))

// ==========================
// Paste Part-3 Below Here
// ==========================

            //==========================
// Logout Button
//==========================

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),

                colors = CardDefaults.cardColors(

                    containerColor = Color(0xFFE50914)

                ),

                onClick = {

                    authViewModel.logout()

                    navController.navigate(MyNavRoutes.LoginScreen){

                        popUpTo(0)

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


@Composable
fun ProfileMenuItem(

    title: String,

    icon: String

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFF1A1A1A)

        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 4.dp

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(

                text = icon,

                fontSize = 22.sp

            )

            Spacer(modifier = Modifier.width(18.dp))

            Text(

                text = title,

                modifier = Modifier.weight(1f),

                color = Color.White,

                fontSize = 17.sp,

                fontWeight = FontWeight.Medium

            )

            Text(

                text = "›",

                color = Color.Gray,

                fontSize = 22.sp

            )

        }

    }

}