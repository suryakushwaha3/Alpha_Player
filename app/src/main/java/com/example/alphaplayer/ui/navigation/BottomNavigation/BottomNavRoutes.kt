package com.example.alphaplayer.ui.navigation.BottomNavigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface BottomNavRoutes {

    @Serializable
    data object Home : BottomNavRoutes

    @Serializable
    data object Movies : BottomNavRoutes

    @Serializable
    data object Watchlist : BottomNavRoutes

    @Serializable
    data object Profile : BottomNavRoutes

    @Serializable
    data class Player(
        val title: String, 
        val url: String, 
        val headersJson: String? = null
    ) : BottomNavRoutes

}