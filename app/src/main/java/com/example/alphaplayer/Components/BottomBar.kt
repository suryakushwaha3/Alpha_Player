package com.example.alphaplayer.Components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AlphaBottomBar(

    selectedIndex: Int,

    onItemSelected: (Int) -> Unit

) {

    NavigationBar(

        modifier = Modifier
            .navigationBarsPadding()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),

        containerColor = Color(0xFF111111),

        tonalElevation = 8.dp

    ) {

        NavigationBarItem(

            selected = selectedIndex == 0,

            onClick = { onItemSelected(0) },

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

        NavigationBarItem(

            selected = selectedIndex == 1,

            onClick = { onItemSelected(1) },

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

        NavigationBarItem(

            selected = selectedIndex == 2,

            onClick = { onItemSelected(2) },

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

        NavigationBarItem(

            selected = selectedIndex == 3,

            onClick = { onItemSelected(3) },

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