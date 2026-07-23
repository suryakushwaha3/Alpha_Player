package com.example.alphaplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alphaplayer.ui.screens.auth.ForgotPasswordScreen
import com.example.alphaplayer.ui.screens.auth.LoginScreen
import com.example.alphaplayer.ui.screens.auth.SignUpScreen
import com.example.alphaplayer.ui.screens.auth.SplashScreen
import com.example.alphaplayer.ui.screens.home.HomeScreen
import com.example.alphaplayer.ui.screens.movies.MoviesScreen
import com.example.alphaplayer.ui.screens.profile.AboutScreen
import com.example.alphaplayer.ui.screens.profile.ChangePasswordScreen
import com.example.alphaplayer.ui.screens.profile.DownloadScreen
import com.example.alphaplayer.ui.screens.profile.EditProfileScreen
import com.example.alphaplayer.ui.screens.profile.PrivacyPolicyScreen
import com.example.alphaplayer.ui.screens.profile.ProfileScreen
import com.example.alphaplayer.ui.screens.watchlist.WatchlistScreen

@Composable
fun MyNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = MyNavRoutes.SplashScreen
    ) {

        composable<MyNavRoutes.SplashScreen> {
            SplashScreen(navController)
        }

        composable<MyNavRoutes.LoginScreen> {

            LoginScreen(

                navController = navController,

                onLoginClick = {
                    navController.navigate(MyNavRoutes.HomeScreen) {
                        popUpTo(MyNavRoutes.LoginScreen) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },

                onSignUpClick = {

                    navController.navigate(MyNavRoutes.SignUpScreen)

                },

                onForgotPasswordClick = {

                    navController.navigate(MyNavRoutes.ForgotPasswordScreen)

                }

            )

        }

        composable<MyNavRoutes.SignUpScreen> {

            SignUpScreen(

                onLoginClick = {
                    navController.navigate(MyNavRoutes.LoginScreen) {
                        popUpTo(MyNavRoutes.SignUpScreen) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onCreateAccountClick = {
                    navController.navigate(MyNavRoutes.HomeScreen) {
                        popUpTo(MyNavRoutes.SignUpScreen) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

            )

        }

        composable<MyNavRoutes.ForgotPasswordScreen> {
            ForgotPasswordScreen(navController)
        }

        composable<MyNavRoutes.HomeScreen> {
            HomeScreen(navController)
        }

        composable<MyNavRoutes.EditProfileScreen> {
            EditProfileScreen(navController)
        }

        composable<MyNavRoutes.ChangePasswordScreen> {
            ChangePasswordScreen(navController)
        }

        composable<MyNavRoutes.DownloadScreen> {
            DownloadScreen(navController)
        }

        composable<MyNavRoutes.PrivacyPolicyScreen> {
            PrivacyPolicyScreen(navController)
        }

        composable<MyNavRoutes.AboutScreen> {
            AboutScreen(navController)
        }

    }

}