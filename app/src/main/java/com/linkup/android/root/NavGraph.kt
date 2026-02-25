package com.linkup.android.root

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.linkup.android.feature.auth.pwchange.ChangePwScreen
import com.linkup.android.feature.auth.pwchange.PwChangeScreen
import com.linkup.android.feature.auth.pwchange.VerifyScreen
import com.linkup.android.feature.auth.signin.SignInScreen
import com.linkup.android.feature.auth.signup.SignUpScreen
import com.linkup.android.feature.home.HomeScreen
import com.linkup.android.ui.components.BottomBar

object NavGroup {

    const val Email = "email"

    const val SignIn = "signIn"
    const val SignUp = "signUp"
    const val Send = "send"

    object Verify {
        const val route = "verify"
        const val routeWithArg = "$route/{$Email}"

        fun createRoute(email: String) = "$route/$email"
    }

    object ChangePw {
        const val route = "changePw"
        const val routeWithArg = "$route/{$Email}"

        fun createRoute(email: String) = "$route/$email"
    }

    const val Home = "home"
}


@Composable
fun AppNavGraph(
    navController: NavHostController,
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val bottomBarRoutes = listOf(
        NavGroup.Home,
//        NavGroup.Qna,
//        NavGroup.Rank
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->

        NavHost(navController = navController, startDestination = NavGroup.Home) {
            composable(NavGroup.SignIn) { SignInScreen(navController) }
            composable(NavGroup.SignUp) { SignUpScreen(navController) }
            composable(NavGroup.Send) { PwChangeScreen(navController) }
            composable(
                route = NavGroup.Verify.routeWithArg,
                arguments = listOf(
                    navArgument(NavGroup.Email) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val email = backStackEntry.arguments
                    ?.getString(NavGroup.Email)
                    .orEmpty()

                VerifyScreen(
                    navController = navController,
                    email = email
                )
            }

            composable(
                route = NavGroup.ChangePw.routeWithArg,
                arguments = listOf(
                    navArgument(NavGroup.Email) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val email = backStackEntry.arguments
                    ?.getString(NavGroup.Email)
                    .orEmpty()

                ChangePwScreen(
                    navController = navController,
                    email = email
                )
            }

            composable(NavGroup.Home) { HomeScreen(navController,innerPadding = innerPadding) }
        }
    }
}