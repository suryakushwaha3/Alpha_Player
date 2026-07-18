package com.example.alphaplayer.Navigation


import kotlinx.serialization.Serializable

@Serializable
sealed interface MyNavRoutes {

    @Serializable
    data object SplashScreen : MyNavRoutes

    @Serializable
    data object LoginScreen : MyNavRoutes

    @Serializable
    data object SignUpScreen : MyNavRoutes

    @Serializable
    data object ForgotPasswordScreen : MyNavRoutes

    @Serializable
    data object HomeScreen : MyNavRoutes

    @Serializable
    data object ProfileScreen : MyNavRoutes
}