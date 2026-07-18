package com.example.alphaplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.alphaplayer.Navigation.MyNavGraph
import com.example.alphaplayer.ui.theme.AlphaPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlphaPlayerTheme {
             MyNavGraph()
            }
        }
    }
}

