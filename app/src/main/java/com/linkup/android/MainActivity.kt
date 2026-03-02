package com.linkup.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.linkup.android.data.datastore.UserRepository
import com.linkup.android.root.AppNavGraph
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.theme.LinkUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class AppStartState {
    LOADING,
    COMPLETE
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LinkUpTheme {
                val navController = rememberNavController()
                var startState by remember { mutableStateOf(AppStartState.LOADING) }

                LaunchedEffect(Unit) {
                    userRepository.loadAccessToken()
                    val token = userRepository.getCachedAccessToken()

                    if (BuildConfig.DEBUG) {
                        Log.d("TOKEN_CHECK", "AccessToken: $token")
                    }

                    startState = AppStartState.COMPLETE


                }

                when (startState) {
                    AppStartState.LOADING -> {
                        AppNavGraph(navController = navController, startDestination = NavGroup.SPLASH)
                    }

                    AppStartState.COMPLETE -> {
                        AppNavGraph(navController = navController, startDestination = NavGroup.HOME)
                    }


                }
            }
        }

    }
}