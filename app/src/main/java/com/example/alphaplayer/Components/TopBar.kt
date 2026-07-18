package com.example.alphaplayer.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun AlphaTopBar(

    searchText: String,

    onSearchTextChange: (String) -> Unit,

    onSearchClick: () -> Unit = {}

) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .statusBarsPadding()
            .padding(vertical = 12.dp)

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),

            horizontalArrangement = Arrangement.Start

        ) {

            Text(

                text = "Alpha",

                color = Color(0xFFE50914),

                fontSize = 30.sp,

                fontWeight = FontWeight.ExtraBold

            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(

                text = "Player",

                color = Color.White,

                fontSize = 30.sp,

                fontWeight = FontWeight.Bold

            )

        }

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(

            value = searchText,

            onValueChange = {

                onSearchTextChange(it)

            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),

            placeholder = {

                Text(

                    text = "Search Movies, Web Series...",

                    color = Color.Gray

                )

            },

            singleLine = true,

            shape = RoundedCornerShape(30.dp),

            leadingIcon = {

                Icon(

                    imageVector = Icons.Default.Search,

                    contentDescription = "Search",

                    tint = Color(0xFFE50914)

                )

            },

            trailingIcon = {

                Icon(

                    imageVector = Icons.Default.Mic,

                    contentDescription = "Mic",

                    tint = Color.White,

                    modifier = Modifier.clickable {

                        onSearchClick()

                    }

                )

            },

            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = Color(0xFF1F1F1F),

                unfocusedContainerColor = Color(0xFF1F1F1F),

                focusedBorderColor = Color(0xFFE50914),

                unfocusedBorderColor = Color.DarkGray,

                focusedTextColor = Color.White,

                unfocusedTextColor = Color.White,

                cursorColor = Color(0xFFE50914),

                focusedPlaceholderColor = Color.Gray,

                unfocusedPlaceholderColor = Color.Gray

            )

        )

        Spacer(modifier = Modifier.height(10.dp))

    }

}