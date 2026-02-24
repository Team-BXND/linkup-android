package com.linkup.android.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.linkup.android.feature.auth.pwchange.ChangePwScreen
import com.linkup.android.feature.auth.pwchange.PwChangeScreen
import com.linkup.android.feature.auth.pwchange.VerifyScreen
import com.linkup.android.feature.auth.signin.SignInScreen
import com.linkup.android.feature.auth.signup.SignUpScreen

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
}


@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NavGroup.SignUp) {
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
    }
}