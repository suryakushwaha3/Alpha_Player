package com.example.alphaplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.example.alphaplayer.data.manager.WatchlistManager
import com.example.alphaplayer.ui.components.PermissionManager
import com.example.alphaplayer.ui.navigation.MyNavGraph
import com.example.alphaplayer.ui.theme.AlphaPlayerTheme

class MainActivity : ComponentActivity() {

    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. EdgeToEdge system bars transparent setup BEFORE setContent
        // Isse recomposition loop aur screen lag completely band ho jayega
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )

        super.onCreate(savedInstanceState)

        // 2. Managers Initialization
        WatchlistManager.init(applicationContext)
        permissionManager = PermissionManager(this)

        setContent {
            // 3. Density recalculation ko remember karke performance boost karein
            val currentDensity = LocalDensity.current
            val customDensity = remember(currentDensity) {
                Density(
                    density = currentDensity.density,
                    // Font Scale 1.15x Max limit taaki layout displace na ho
                    fontScale = currentDensity.fontScale.coerceAtMost(1.15f)
                )
            }

            CompositionLocalProvider(
                LocalDensity provides customDensity
            ) {
                AlphaPlayerTheme {
                    MyNavGraph()
                }
            }
        }
    }
}