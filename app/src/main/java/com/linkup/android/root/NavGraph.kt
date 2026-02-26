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
import com.linkup.android.feature.profile.ActivityType
import com.linkup.android.feature.profile.ProfileScreen
import com.linkup.android.feature.profile.UserActivityScreen
import com.linkup.android.feature.rank.RankScreen
import com.linkup.android.feature.splash.SplashScreen
import com.linkup.android.ui.components.BottomBar

object NavGroup {

    const val EMAIL = "email"
    const val ACTIVITY_TYPE = "activityType"

    const val SPLASH = "splash"
    const val SIGNIN = "signIn"
    const val SIGNUP = "signUp"
    const val SEND = "send"
    const val RANK = "rank"
    const val HOME = "home"
    const val PROFILE = "profile"

    object Verify {
        const val ROUTE = "verify"
        const val ROUTE_WITH_ARG = "$ROUTE/{$EMAIL}"
        fun createRoute(email: String) = "$ROUTE/$email"
    }

    object ChangePw {
        const val ROUTE = "changePw"
        const val ROUTE_WITH_ARG = "$ROUTE/{$EMAIL}"
        fun createRoute(email: String) = "$ROUTE/$email"
    }

    object UserActivity {
        const val ROUTE = "userActivity"
        const val ROUTE_WITH_ARG = "$ROUTE/{$ACTIVITY_TYPE}"
        fun createRoute(activityType: ActivityType) = "$ROUTE/${activityType.name}"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val bottomBarRoutes = listOf(NavGroup.HOME, NavGroup.RANK, NavGroup.PROFILE)

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = NavGroup.HOME, modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
            composable(NavGroup.SIGNIN) { SignInScreen(navController) }
            composable(NavGroup.SIGNUP) { SignUpScreen(navController) }
            composable(NavGroup.SEND) { PwChangeScreen(navController) }
            composable(NavGroup.SPLASH) { SplashScreen(navController) }
            composable(NavGroup.RANK) { RankScreen(navController) }
            composable(NavGroup.PROFILE) { ProfileScreen(navController) }
            composable(NavGroup.HOME) { HomeScreen(navController) }

            composable(
                route = NavGroup.Verify.ROUTE_WITH_ARG,
                arguments = listOf(navArgument(NavGroup.EMAIL) { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString(NavGroup.EMAIL).orEmpty()
                VerifyScreen(navController = navController, email = email)
            }

            composable(
                route = NavGroup.ChangePw.ROUTE_WITH_ARG,
                arguments = listOf(navArgument(NavGroup.EMAIL) { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString(NavGroup.EMAIL).orEmpty()
                ChangePwScreen(navController = navController, email = email)
            }

            composable(
                route = NavGroup.UserActivity.ROUTE_WITH_ARG,
                arguments = listOf(navArgument(NavGroup.ACTIVITY_TYPE) { type = NavType.StringType })
            ) { backStackEntry ->
                val typeString = backStackEntry.arguments?.getString(NavGroup.ACTIVITY_TYPE).orEmpty()
                val activityType = ActivityType.valueOf(typeString)
                UserActivityScreen(activityType = activityType)
            }
        }
    }
}
