package com.linkup.android.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.linkup.android.feature.auth.pwchange.NewPwScreen
import com.linkup.android.feature.auth.pwchange.PwChangeScreen
import com.linkup.android.feature.auth.pwchange.VerifyScreen
import com.linkup.android.feature.auth.signin.SignInScreen
import com.linkup.android.feature.auth.signup.SignUpScreen

object NavGroup {
    const val SignIn = "signIn"
    const val SignUp = "signUp"
    const val PwChange = "pwChange"
    const val NewPw = "newPw"
    const val Verify = "verify"

}

@Composable
fun AppNavGraph(
    navController: NavHostController,
){
    NavHost(navController = navController, startDestination = NavGroup.SignIn){
        composable(NavGroup.SignIn) { SignInScreen(navController) }
        composable(NavGroup.SignUp) { SignUpScreen(navController) }
        composable(NavGroup.PwChange) { PwChangeScreen(navController) }
        composable(NavGroup.NewPw){ NewPwScreen(navController) }
        composable(NavGroup.Verify){ VerifyScreen(navController) }
    }

}