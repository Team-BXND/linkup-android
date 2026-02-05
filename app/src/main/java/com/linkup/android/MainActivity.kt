package com.linkup.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.linkup.android.data.datastore.UserRepository
import com.linkup.android.feature.auth.signin.SignInScreen
import com.linkup.android.root.AppNavGraph
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.theme.LinkUpTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LinkUpTheme {
                val navController = rememberNavController()
                var tokenLoaded by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    userRepository.loadAccessToken()
                    tokenLoaded = true
                }

                if (tokenLoaded) {
                    AppNavGraph(
                        navController = navController
                    )
                } else {
                    SignInScreen(navController)
                }
            }
        }
    }
}