package com.example.alphaplayer.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes

@Composable
fun AlphaBottomBar(

    navController: NavHostController

) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route

    val shouldShowBottomBar = currentRoute?.contains("Player") != true

    if (!shouldShowBottomBar) {
        return
    }

    NavigationBar(

        modifier = Modifier
            .navigationBarsPadding()
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                )
            ),

        containerColor = Color(0xFF111111),

        tonalElevation = 8.dp

    ) {

        // Home
        NavigationBarItem(

            selected = currentRoute == BottomNavRoutes.Home::class.qualifiedName,

            onClick = {

                navController.navigate(BottomNavRoutes.Home) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true

                    restoreState = true

                }

            },

            icon = {

                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home"
                )

            },

            label = {

                Text("Home")

            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFFE50914),

                selectedTextColor = Color(0xFFE50914),

                indicatorColor = Color(0x33E50914),

                unselectedIconColor = Color.Gray,

                unselectedTextColor = Color.Gray

            )

        )

        // Movies
        NavigationBarItem(

            selected = currentRoute == BottomNavRoutes.Movies::class.qualifiedName,

            onClick = {

                navController.navigate(BottomNavRoutes.Movies) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true

                    restoreState = true

                }

            },

            icon = {

                Icon(
                    imageVector = Icons.Outlined.LocalMovies,
                    contentDescription = "Movies"
                )

            },

            label = {

                Text("Movies")

            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFFE50914),

                selectedTextColor = Color(0xFFE50914),

                indicatorColor = Color(0x33E50914),

                unselectedIconColor = Color.Gray,

                unselectedTextColor = Color.Gray

            )

        )

        // Watchlist
        NavigationBarItem(

            selected = currentRoute == BottomNavRoutes.Watchlist::class.qualifiedName,

            onClick = {

                navController.navigate(BottomNavRoutes.Watchlist) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true

                    restoreState = true

                }

            },

            icon = {

                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Watchlist"
                )

            },

            label = {

                Text("Watchlist")

            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFFE50914),

                selectedTextColor = Color(0xFFE50914),

                indicatorColor = Color(0x33E50914),

                unselectedIconColor = Color.Gray,

                unselectedTextColor = Color.Gray

            )

        )

        // Profile
        NavigationBarItem(

            selected = currentRoute == BottomNavRoutes.Profile::class.qualifiedName,

            onClick = {

                navController.navigate(BottomNavRoutes.Profile) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true

                    restoreState = true

                }

            },

            icon = {

                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile"
                )

            },

            label = {

                Text("Profile")

            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFFE50914),

                selectedTextColor = Color(0xFFE50914),

                indicatorColor = Color(0x33E50914),

                unselectedIconColor = Color.Gray,

                unselectedTextColor = Color.Gray

            )

        )

    }

}