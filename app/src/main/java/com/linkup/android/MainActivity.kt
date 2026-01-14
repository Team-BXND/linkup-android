package com.linkup.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.linkup.android.root.AppNavGraph
import com.linkup.android.ui.theme.LinkUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinkUpTheme {
                AppNavGraph(navController = rememberNavController())
            }
        }
    }
}