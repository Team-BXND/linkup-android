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
    const val SignIn = "signIn"
    const val SignUp = "signUp"
    const val Send = "send"
    const val ChangePw = "changePw/{email}"
    const val Verify = "verify/{email}"

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
            route = NavGroup.Verify, arguments = listOf(
            navArgument("email") { type = NavType.StringType })) { backStackEntry ->

            val email = backStackEntry.arguments?.getString("email")

            VerifyScreen(
                navController = navController, email = email ?: ""
            )
        }

        composable(
            route = NavGroup.ChangePw, arguments = listOf(
                navArgument("email") { type = NavType.StringType })) { backStackEntry ->

            val email = backStackEntry.arguments?.getString("email")

            ChangePwScreen(
                navController = navController, email = email ?: ""
            )
        }

    }

}