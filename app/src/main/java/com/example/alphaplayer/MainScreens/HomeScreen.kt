package com.example.alphaplayer.MainScreens


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.alphaplayer.Components.AlphaBottomBar
import com.example.alphaplayer.Components.AlphaTopBar
import com.example.alphaplayer.Components.HomeContent

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    var searchText by remember { mutableStateOf("")}

    var selectedItem by remember {

        mutableStateOf(0)

    }

    Scaffold(

        containerColor = Color.Black,

        topBar = {

            if (selectedItem == 0) {

                AlphaTopBar(

                    searchText = searchText,

                    onSearchTextChange = {

                        searchText = it

                    },

                    onSearchClick = {

                        // Voice Search

                    }

                )

            }

        },

        bottomBar = {

            AlphaBottomBar(

                selectedIndex = selectedItem,

                onItemSelected = {

                    selectedItem = it

                }

            )

        }

    ) { padding ->

        when (selectedItem) {

            0 -> HomeContent(padding)

            1 -> MoviesScreen()

            2 -> WatchlistScreen()

            3 -> ProfileScreen( navController = navController)

        }

    }

}