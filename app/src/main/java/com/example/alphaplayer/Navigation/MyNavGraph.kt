package com.example.alphaplayer.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alphaplayer.AuthScreens.ForgetPasswordScreen
import com.example.alphaplayer.MainScreens.HomeScreen
import com.example.alphaplayer.AuthScreens.LoginScreen
import com.example.alphaplayer.AuthScreens.SignUpScreen
import com.example.alphaplayer.AuthScreens.SplashScreen
import com.example.alphaplayer.MainScreens.ProfileScreen

@Composable
fun MyNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MyNavRoutes.SplashScreen
    ) {

        // Splash Screen
        composable<MyNavRoutes.SplashScreen> {

            SplashScreen(navController)

        }

        // Login Screen
        composable<MyNavRoutes.LoginScreen> {

            LoginScreen(

                navController = navController,

                onLoginClick = {

                    navController.navigate(MyNavRoutes.HomeScreen) {

                        popUpTo<MyNavRoutes.LoginScreen> {
                            inclusive = true
                        }

                    }

                },

                onSignUpClick = {

                    navController.navigate(MyNavRoutes.SignUpScreen)

                },

                onForgotPasswordClick = {

                    navController.navigate(
                        MyNavRoutes.ForgotPasswordScreen
                    )

                }

            )

        }

        // Sign Up Screen
        composable<MyNavRoutes.SignUpScreen> {

            SignUpScreen(

                onLoginClick = {

                    navController.navigate(MyNavRoutes.LoginScreen) {

                        popUpTo(MyNavRoutes.SignUpScreen) {
                            inclusive = true
                        }

                    }

                },

                onCreateAccountClick = {

                    navController.navigate(MyNavRoutes.HomeScreen) {

                        popUpTo(MyNavRoutes.SignUpScreen) {
                            inclusive = true
                        }

                    }

                }

            )

        }

        // Forgot Password Screen
        composable<MyNavRoutes.ForgotPasswordScreen> {

            ForgetPasswordScreen(
                navController = navController
            )

        }

        //Profile Screen
        composable<MyNavRoutes.ProfileScreen> {

            ProfileScreen(
                navController = navController
            )

        }

        // Home Screen
        composable<MyNavRoutes.HomeScreen> {

            HomeScreen(navController)

        }

    }

}