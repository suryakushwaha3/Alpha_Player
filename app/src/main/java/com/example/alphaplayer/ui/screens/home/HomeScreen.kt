//package com.example.alphaplayer.ui.screens.home
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.alphaplayer.ui.components.AlphaBottomBar
//import com.example.alphaplayer.ui.components.AlphaTopBar
//import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavGraph
//import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
//@Composable
//fun HomeScreen(
//    navController: NavHostController
//) {
//    var searchText by remember { mutableStateOf("") }
//    val bottomNavController = rememberNavController()
//    val backStackEntry by bottomNavController.currentBackStackEntryAsState()
//    val currentRoute = backStackEntry?.destination?.route
//
//    // Null initially to show loader until Firebase responds
//    var playlistUrl by remember { mutableStateOf<String?>(null) }
//    var isError by remember { mutableStateOf(false) }
//
//    // Fetch M3U Playlist URL from Firebase Firestore
//    LaunchedEffect(Unit) {
//        try {
//            val document = FirebaseFirestore.getInstance()
//                .collection("settings")
//                .document("playlist")
//                .get()
//                .await()
//
//            val url = document.getString("playlistUrl")
//            if (!url.isNullOrBlank()) {
//                playlistUrl = url
//                isError = false
//            } else {
//                playlistUrl = ""
//                isError = true
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            playlistUrl = ""
//            isError = true
//        }
//    }
//
//    // TopBar aur BottomBar kab dikhane hain
//    val isHomeRoute = currentRoute == BottomNavRoutes.Home::class.qualifiedName
//
//    Scaffold(
//        containerColor = Color.Black,
//        topBar = {
//            if (isHomeRoute) {
//                AlphaTopBar(
//                    searchText = searchText,
//                    onSearchTextChange = {
//                        searchText = it
//                    },
//                    onSearchClick = {
//                        // Handle search action
//                    }
//                )
//            }
//        },
//        bottomBar = {
//            // Player screen par bottom bar hide kar sakte hain agar zaroorat ho
//            AlphaBottomBar(
//                navController = bottomNavController
//            )
//        }
//    ) { padding ->
//        val currentPlaylistUrl = playlistUrl
//
//        when {
//            // 1. Initial Firebase Fetch Loading State
//            currentPlaylistUrl == null -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Black)
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = Color(0xFFE50914))
//                }
//            }
//
//            // 2. Firebase Link Fetch Fail / Empty URL State
//            isError && currentPlaylistUrl.isBlank() -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Black)
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Failed to load playlist configuration.",
//                        color = Color.Gray,
//                        fontSize = 15.sp
//                    )
//                }
//            }
//
//            // 3. Main Navigation Graph
//            else -> {
//                BottomNavGraph(
//                    bottomNavController = bottomNavController,
//                    mainNavController = navController,
//                    padding = padding,
//                    playlistUrl = currentPlaylistUrl,
//                    searchText = searchText
//                )
//            }
//        }
//    }
//}
//
//

// updated


//package com.example.alphaplayer.ui.screens.home
//
//import android.app.Activity
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalView
//import androidx.compose.ui.unit.sp
//import androidx.core.view.WindowCompat
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.alphaplayer.ui.components.AlphaBottomBar
//import com.example.alphaplayer.ui.components.AlphaTopBar
//import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavGraph
//import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
//// Helper Composable function to detect Orientation
//@Composable
//fun rememberIsLandscape(): Boolean {
//    val configuration = LocalConfiguration.current
//    return configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
//}
//
//@Composable
//fun HomeScreen(
//    navController: NavHostController
//) {
//    // Fixed Status Bar Icons visibility for Dark Background (White Icons)
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
//        }
//    }
//
//    var searchText by remember { mutableStateOf("") }
//    val bottomNavController = rememberNavController()
//    val backStackEntry by bottomNavController.currentBackStackEntryAsState()
//    val currentRoute = backStackEntry?.destination?.route
//
//    // Screen kholte hi empty string rakhi hai taaki direct shimmer layout load ho
//    var playlistUrl by remember { mutableStateOf("") }
//    var isError by remember { mutableStateOf(false) }
//
//    // Watchlist items store karne ke liye state
//    var watchlistItems by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
//
//    // Fetch M3U Playlist URL and Watchlist data from Firebase Firestore in background
//    LaunchedEffect(Unit) {
//        // 1. Fetch Playlist URL
//        try {
//            val document = FirebaseFirestore.getInstance()
//                .collection("settings")
//                .document("playlist")
//                .get()
//                .await()
//
//            val url = document.getString("playlistUrl")
//            if (!url.isNullOrBlank()) {
//                playlistUrl = url
//                isError = false
//            } else {
//                isError = true
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            isError = true
//        }
//
//        // 2. Fetch User Watchlist Data
//        try {
//            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
//            if (currentUserId != null) {
//                val snapshot = FirebaseFirestore.getInstance()
//                    .collection("users")
//                    .document(currentUserId)
//                    .collection("watchlist")
//                    .get()
//                    .await()
//
//                watchlistItems = snapshot.documents.mapNotNull { it.data }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // TopBar aur BottomBar kab dikhane hain
//    val isHomeRoute = currentRoute == BottomNavRoutes.Home::class.qualifiedName
//
//    Scaffold(
//        containerColor = Color.Black,
//        topBar = {
//            if (isHomeRoute) {
//                AlphaTopBar(
//                    searchText = searchText,
//                    onSearchTextChange = {
//                        searchText = it
//                    },
//                    onSearchClick = {
//                        // Handle search action
//                    }
//                )
//            }
//        },
//        bottomBar = {
//            AlphaBottomBar(
//                navController = bottomNavController
//            )
//        }
//    ) { padding ->
//        when {
//            // Sirf tab error message dikhayega jab Firebase fetch fail ho jaye aur URL na miley
//            isError && playlistUrl.isEmpty() -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Black)
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Failed to load playlist configuration.",
//                        color = Color.Gray,
//                        fontSize = 15.sp
//                    )
//                }
//            }
//
//            // Directly BottomNavGraph render hoga bina kisi 1 sec ke CircularProgressIndicator ke
//            else -> {
//                BottomNavGraph(
//                    bottomNavController = bottomNavController,
//                    mainNavController = navController,
//                    padding = padding,
//                    playlistUrl = playlistUrl,
//                    searchText = searchText
//                )
//            }
//        }
//    }
//}

package com.example.alphaplayer.ui.screens.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alphaplayer.ui.components.AlphaBottomBar
import com.example.alphaplayer.ui.components.AlphaTopBar
import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavGraph
import com.example.alphaplayer.ui.navigation.BottomNavigation.BottomNavRoutes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Helper Composable function to detect Orientation
@Composable
fun rememberIsLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
}

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    // Fixed Status Bar Icons visibility for Dark Background (White Icons)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var searchText by remember { mutableStateOf("") }
    val bottomNavController = rememberNavController()
    val backStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Screen kholte hi empty string rakhi hai taaki direct shimmer layout load ho
    var playlistUrl by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    // Watchlist items store karne ke liye state
    var watchlistItems by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Fetch M3U Playlist URL and Watchlist data from Firebase Firestore in background
    LaunchedEffect(Unit) {
        // 1. Fetch Playlist URL
        try {
            val document = FirebaseFirestore.getInstance()
                .collection("settings")
                .document("playlist")
                .get()
                .await()

            val url = document.getString("playlistUrl")
            if (!url.isNullOrBlank()) {
                playlistUrl = url
                isError = false
            } else {
                isError = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            isError = true
        }

        // 2. Fetch User Watchlist Data
        try {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUserId != null) {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(currentUserId)
                    .collection("watchlist")
                    .get()
                    .await()

                watchlistItems = snapshot.documents.mapNotNull { it.data }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 🔥 FIX: TopBar ab Home AUR Movies dono routes par VISIBLE rahega.
    // Isse Home se Movies me switch karne par TopBar destroy/re-render nahi hoga aur flicker khatam ho jayega.
    val isTopBarVisible = currentRoute == BottomNavRoutes.Home::class.qualifiedName ||
            currentRoute == BottomNavRoutes.Movies::class.qualifiedName

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            if (isTopBarVisible) {
                AlphaTopBar(
                    searchText = searchText,
                    onSearchTextChange = {
                        searchText = it
                    },
                    onSearchClick = {
                        // Handle search action
                    }
                )
            }
        },
        bottomBar = {
            AlphaBottomBar(
                navController = bottomNavController
            )
        }
    ) { padding ->
        when {
            // Sirf tab error message dikhayega jab Firebase fetch fail ho jaye aur URL na miley
            isError && playlistUrl.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Failed to load playlist configuration.",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                }
            }

            // Directly BottomNavGraph render hoga bina kisi 1 sec ke CircularProgressIndicator ke
            else -> {
                BottomNavGraph(
                    bottomNavController = bottomNavController,
                    mainNavController = navController,
                    padding = padding,
                    playlistUrl = playlistUrl,
                    searchText = searchText
                )
            }
        }
    }
}