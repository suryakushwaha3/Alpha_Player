////package com.example.alphaplayer.ui.components
////
////import androidx.compose.foundation.layout.*
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.Search
////import androidx.compose.material3.*
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////
////@Composable
////fun AlphaTopBar(
////    searchText: String,
////    onSearchTextChange: (String) -> Unit,
////    onSearchClick: () -> Unit = {}
////) {
////    Surface(
////        color = Color.Black,
////        modifier = Modifier.fillMaxWidth()
////    ) {
////        Column(
////            modifier = Modifier
////                .padding(horizontal = 16.dp, vertical = 40.dp)
////        ) {
////            Row(
////                verticalAlignment = Alignment.CenterVertically,
////                modifier = Modifier.fillMaxWidth(),
////                horizontalArrangement = Arrangement.SpaceBetween
////            ) {
////                Text(
////                    text = "Alpha Player",
////                    fontSize = 28.sp,
////                    fontWeight = FontWeight.Bold,
////                    color = Color(0xFFE50914)
////                )
////
////
////            }
////
////            Spacer(modifier = Modifier.height(8.dp))
////
////            OutlinedTextField(
////                value = searchText,
////                onValueChange = onSearchTextChange,
////                modifier = Modifier.fillMaxWidth(),
////                placeholder = {
////                    Text(
////                        text = "Search movies...",
////                        color = Color.Gray,
////                        fontSize = 14.sp
////                    )
////                },
////                trailingIcon = {
////                    Icon(
////                        imageVector = Icons.Default.Search,
////                        contentDescription = null,
////                        tint = Color.Gray
////                    )
////                },
////                singleLine = true,
////                colors = OutlinedTextFieldDefaults.colors(
////                    focusedContainerColor = Color(0xFF1A1A1A),
////                    unfocusedContainerColor = Color(0xFF1A1A1A),
////                    focusedBorderColor = Color(0xFFE50914),
////                    unfocusedBorderColor = Color.Transparent,
////                    cursorColor = Color(0xFFE50914),
////                    focusedTextColor = Color.White,
////                    unfocusedTextColor = Color.White
////                ),
////                shape = MaterialTheme.shapes.medium
////            )
////        }
////    }
////}
//
//package com.example.alphaplayer.ui.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun AlphaTopBar(
//    searchText: String,
//    onSearchTextChange: (String) -> Unit,
//    onSearchClick: () -> Unit = {}
//) {
//    Surface(
//        color = Color.Black,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .statusBarsPadding() // 1. Battery, time aur network icons ke niche se start karega
//                .padding(horizontal = 16.dp, vertical = 12.dp) // 2. Fixed 40.dp ki jagah 12.dp clean padding
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Alpha Player",
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFE50914)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            OutlinedTextField(
//                value = searchText,
//                onValueChange = onSearchTextChange,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = {
//                    Text(
//                        text = "Search movies...",
//                        color = Color.Gray,
//                        fontSize = 14.sp
//                    )
//                },
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = null,
//                        tint = Color.Gray
//                    )
//                },
//                singleLine = true,
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedContainerColor = Color(0xFF1A1A1A),
//                    unfocusedContainerColor = Color(0xFF1A1A1A),
//                    focusedBorderColor = Color(0xFFE50914),
//                    unfocusedBorderColor = Color.Transparent,
//                    cursorColor = Color(0xFFE50914),
//                    focusedTextColor = Color.White,
//                    unfocusedTextColor = Color.White
//                ),
//                shape = MaterialTheme.shapes.medium
//            )
//        }
//    }
//}

package com.example.alphaplayer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlphaTopBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    placeholderText: String = "Search...",
    onSearchClick: () -> Unit = {}
) {
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Alpha Player",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE50914)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = placeholderText,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1A1A1A),
                    unfocusedContainerColor = Color(0xFF1A1A1A),
                    focusedBorderColor = Color(0xFFE50914),
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFFE50914),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}
