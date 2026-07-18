package com.example.alphaplayer.AuthScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.Auth.AuthViewModel
import com.example.alphaplayer.Navigation.MyNavRoutes
import com.example.alphaplayer.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
) {

    val authViewModel: AuthViewModel = viewModel()

    LaunchedEffect(Unit) {

        delay(3000)

        if (authViewModel.isUserLoggedIn()) {

            navController.navigate(MyNavRoutes.HomeScreen) {

                popUpTo(MyNavRoutes.SplashScreen) {
                    inclusive = true
                }

                launchSingleTop = true

            }

        } else {

            navController.navigate(MyNavRoutes.LoginScreen) {

                popUpTo(MyNavRoutes.SplashScreen) {
                    inclusive = true
                }

                launchSingleTop = true

            }

        }

    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Image(

            painter = painterResource(id = R.drawable.alpha),

            contentDescription = "App Logo",

            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(24.dp))

        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(

            text = "Alpha Player",

            fontSize = 34.sp,

            fontWeight = FontWeight.Bold,

            color = Color.White

        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(

            text = "Movies • Web Series • Entertainment",

            fontSize = 16.sp,

            color = Color.LightGray

        )

    }

}