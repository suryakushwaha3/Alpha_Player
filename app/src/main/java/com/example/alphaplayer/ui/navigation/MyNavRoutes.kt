package com.example.alphaplayer.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MyNavRoutes {
    @Serializable data object SplashScreen : MyNavRoutes
    @Serializable data object LoginScreen : MyNavRoutes
    @Serializable data object SignUpScreen : MyNavRoutes
    @Serializable data object ForgotPasswordScreen : MyNavRoutes

    @Serializable data object HomeScreen : MyNavRoutes

    @Serializable data object EditProfileScreen : MyNavRoutes
    @Serializable data object ChangePasswordScreen : MyNavRoutes
    @Serializable data object DownloadScreen : MyNavRoutes
    @Serializable data object PrivacyPolicyScreen : MyNavRoutes
    @Serializable data object AboutScreen : MyNavRoutes
}
