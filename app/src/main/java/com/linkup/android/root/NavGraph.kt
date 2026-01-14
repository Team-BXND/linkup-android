package com.linkup.android.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.linkup.android.feature.auth.signin.SignInScreen
import com.linkup.android.feature.auth.signup.SignUpScreen

object NavGroup {
    const val SignIn = "signIn"
    const val SignUp = "signUp"

}

@Composable
fun AppNavGraph(
    navController: NavHostController,
){
    NavHost(navController = navController, startDestination = NavGroup.SignIn){
        composable(NavGroup.SignIn) { SignInScreen(navController) }
        composable(NavGroup.SignUp) { SignUpScreen(navController) }
    }

}