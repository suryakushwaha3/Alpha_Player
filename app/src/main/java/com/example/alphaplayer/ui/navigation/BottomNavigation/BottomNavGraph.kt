//package com.example.alphaplayer.ui.navigation.BottomNavigation
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.toRoute
//import com.example.alphaplayer.ui.screens.home.HomeContent
//import com.example.alphaplayer.ui.screens.movies.MoviesScreen
//import com.example.alphaplayer.ui.screens.player.PlayerScreen
//import com.example.alphaplayer.ui.screens.profile.ProfileScreen
//import com.example.alphaplayer.ui.screens.watchlist.WatchlistScreen
//
//
//@Composable
//fun BottomNavGraph(
//    bottomNavController: NavHostController,
//    mainNavController: NavHostController,
//    padding: PaddingValues,
//    playlistUrl: String?,
//    searchText: String
//
//) {
//    NavHost(
//
//        navController = bottomNavController,
//
//        startDestination = BottomNavRoutes.Home
//
//    ) {
//
//        composable<BottomNavRoutes.Home> {
//
//            if (playlistUrl == null) {
//
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//
//            } else {
//
//
//                HomeContent(
//                    padding = padding,
//                    playlistUrl = playlistUrl,
//                    navController = bottomNavController,
//                    searchText = searchText
//                )
//
//            }
//
//        }
//
//
//        composable<BottomNavRoutes.Player> { backStackEntry ->
//
//            val player: BottomNavRoutes.Player = backStackEntry.toRoute()
//
//            PlayerScreen(
//                title = player.title,
//                url = player.url,
//                headersJson = player.headersJson,
//                navController = bottomNavController
//            )
//        }
//
//        composable<BottomNavRoutes.Movies> {
//            MoviesScreen(bottomNavController)
//        }
//
//        composable<BottomNavRoutes.Watchlist> {
//            WatchlistScreen(bottomNavController)
//        }
//
//        composable<BottomNavRoutes.Profile> {
//            ProfileScreen(
//                navController = mainNavController,
//                bottomNavController = bottomNavController
//            )
//        }
//
//    }
//
//}


//package com.example.alphaplayer.ui.navigation.BottomNavigation
//
//import androidx.compose.animation.EnterTransition
//import androidx.compose.animation.ExitTransition
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.toRoute
//import com.example.alphaplayer.ui.screens.home.HomeContent
//import com.example.alphaplayer.ui.screens.movies.MoviesScreen
//import com.example.alphaplayer.ui.screens.player.PlayerScreen
//import com.example.alphaplayer.ui.screens.profile.ProfileScreen
//import com.example.alphaplayer.ui.screens.watchlist.WatchlistScreen
//
//@Composable
//fun BottomNavGraph(
//    bottomNavController: NavHostController,
//    mainNavController: NavHostController,
//    padding: PaddingValues,
//    playlistUrl: String?,
//    searchText: String // Main/Parent screen se aaya hua search query
//) {
//    NavHost(
//        navController = bottomNavController,
//        startDestination = BottomNavRoutes.Home,
//        // All Screens Fix: Sabhi bottom tabs ke liye animation flickering disable ki gayi hai
//        enterTransition = { EnterTransition.None },
//        exitTransition = { ExitTransition.None },
//        popEnterTransition = { EnterTransition.None },
//        popExitTransition = { ExitTransition.None }
//    ) {
//        composable<BottomNavRoutes.Home> {
//            if (playlistUrl == null) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            } else {
//                HomeContent(
//                    padding = padding,
//                    playlistUrl = playlistUrl,
//                    navController = bottomNavController,
//                    searchText = searchText
//                )
//            }
//        }
//
//        composable<BottomNavRoutes.Player> { backStackEntry ->
//            val player: BottomNavRoutes.Player = backStackEntry.toRoute()
//
//            PlayerScreen(
//                title = player.title,
//                url = player.url,
//                headersJson = player.headersJson,
//                navController = bottomNavController
//            )
//        }
//
//        composable<BottomNavRoutes.Movies> {
//            MoviesScreen(
//                navController = bottomNavController,
//                padding = padding,
//                searchText = searchText
//            )
//        }
//
//        composable<BottomNavRoutes.Watchlist> {
//            // Box wrapper added with padding to prevent top bar / bottom bar content jump
//            Box(modifier = Modifier.padding(padding)) {
//                WatchlistScreen(bottomNavController)
//            }
//        }
//
//        composable<BottomNavRoutes.Profile> {
//            // Box wrapper added with padding to prevent top bar / bottom bar content jump
//            Box(modifier = Modifier.padding(padding)) {
//                ProfileScreen(
//                    navController = mainNavController,
//                    bottomNavController = bottomNavController
//                )
//            }
//        }
//    }
//}

package com.example.alphaplayer.ui.navigation.BottomNavigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.alphaplayer.ui.screens.home.HomeContent
import com.example.alphaplayer.ui.screens.movies.MoviesScreen
import com.example.alphaplayer.ui.screens.player.PlayerScreen
import com.example.alphaplayer.ui.screens.profile.ProfileScreen
import com.example.alphaplayer.ui.screens.watchlist.WatchlistScreen

@Composable
fun BottomNavGraph(
    bottomNavController: NavHostController,
    mainNavController: NavHostController,
    padding: PaddingValues,
    playlistUrl: String?,
    searchText: String
) {
    NavHost(
        navController = bottomNavController,
        startDestination = BottomNavRoutes.Home,
        // Disable transitions to fix screen flip / flicker
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<BottomNavRoutes.Home> {
            if (playlistUrl == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                HomeContent(
                    padding = padding,
                    playlistUrl = playlistUrl,
                    navController = bottomNavController,
                    searchText = searchText
                )
            }
        }

        composable<BottomNavRoutes.Player> { backStackEntry ->
            val player: BottomNavRoutes.Player = backStackEntry.toRoute()

            PlayerScreen(
                title = player.title,
                url = player.url,
                headersJson = player.headersJson,
                navController = bottomNavController
            )
        }

        composable<BottomNavRoutes.Movies> {
            MoviesScreen(
                navController = bottomNavController,
                padding = padding,
                searchText = searchText
            )
        }

        composable<BottomNavRoutes.Watchlist> {
            Box(modifier = Modifier.padding(padding)) {
                WatchlistScreen(bottomNavController)
            }
        }

        composable<BottomNavRoutes.Profile> {
            Box(modifier = Modifier.padding(padding)) {
                ProfileScreen(
                    navController = mainNavController,
                    bottomNavController = bottomNavController
                )
            }
        }
    }
}