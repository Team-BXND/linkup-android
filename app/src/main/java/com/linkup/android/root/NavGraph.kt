package com.linkup.android.root

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.linkup.android.feature.auth.AuthViewModel
import com.linkup.android.feature.auth.pwchange.ChangePwScreen
import com.linkup.android.feature.auth.pwchange.VerifyScreen
import com.linkup.android.feature.auth.signin.SignInScreen
import com.linkup.android.feature.auth.signup.SignUpScreen
import com.linkup.android.feature.home.HomeScreen
import com.linkup.android.feature.post.PostDetailScreen
import com.linkup.android.feature.qna.QnaScreen
import com.linkup.android.feature.profile.ActivityType
import com.linkup.android.feature.profile.MoveToAuthScreen
import com.linkup.android.feature.profile.ProfileScreen
import com.linkup.android.feature.profile.UserActivityScreen
import com.linkup.android.feature.rank.RankScreen
import com.linkup.android.feature.splash.SplashScreen
import com.linkup.android.feature.write.WriteScreen
import com.linkup.android.network.Category
import com.linkup.android.ui.components.BottomBar

object NavGroup {

    const val EMAIL = "email"
    const val RANK = "rank"
    const val ACTIVITY_TYPE = "activityType"

    const val SPLASH = "splash"
    const val SIGNIN = "signIn"
    const val SIGNUP = "signUp"
    const val SEND = "send"
    const val PROFILE = "profile"
    const val MOVETOAUTH = "movetoauth"


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

    const val HOME = "home"

    const val QNA = "qna"

    const val DETAIL = "detail"

    const val WRITE = "write"

    const val WRITE_ID = "write/{postId}"

    const val QNA_WITH_CATEGORY = "qna/{category}"

    fun createQnaRoute(category: String) = "qna/$category"
    object UserActivity {
        const val ROUTE = "userActivity"
        const val ROUTE_WITH_ARG = "$ROUTE/{$ACTIVITY_TYPE}"
        fun createRoute(activityType: ActivityType) = "$ROUTE/${activityType.name}"
    }
}


@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val authViewModel: AuthViewModel = hiltViewModel()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val bottomBarRoutes = listOf(
        NavGroup.HOME,
        NavGroup.RANK,
        NavGroup.QNA,
        NavGroup.DETAIL,
        NavGroup.WRITE,
        NavGroup.PROFILE,
        NavGroup.MOVETOAUTH
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomBar(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            composable(NavGroup.SPLASH) {
                SplashScreen(navController)
            }

            composable(NavGroup.SIGNIN) {
                SignInScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }

            composable(NavGroup.SIGNUP) {
                SignUpScreen(navController)
            }

            composable(NavGroup.SEND) {
                ChangePwScreen(navController)
            }

            composable(NavGroup.HOME) {
                HomeScreen(
                    navController,
                    innerPadding = innerPadding
                )
            }

            composable(NavGroup.QNA) {
                QnaScreen(
                    navController = navController,
                    innerPadding = innerPadding,
                    initialCategory = Category.ALL,
                    authViewModel
                )
            }

            composable(NavGroup.RANK) {
                RankScreen(navController, innerPadding = innerPadding)
            }

            composable(NavGroup.WRITE) {
                WriteScreen(navController, innerPadding)
            }

            composable (NavGroup.PROFILE) {
                ProfileScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }

            composable(NavGroup.MOVETOAUTH) {
                MoveToAuthScreen(navController,innerPadding)
            }

            composable(
                "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("id") ?: 0
                PostDetailScreen(
                    navController = navController,
                    innerPadding = innerPadding,
                    postId = postId
                )
            }

            composable(
                route = NavGroup.Verify.ROUTE_WITH_ARG,
                arguments = listOf(
                    navArgument(NavGroup.EMAIL) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val email = backStackEntry.arguments
                    ?.getString(NavGroup.EMAIL)
                    .orEmpty()

                VerifyScreen(
                    navController = navController,
                    email = email
                )
            }

            composable(
                route = NavGroup.UserActivity.ROUTE_WITH_ARG,
                arguments = listOf(navArgument(NavGroup.ACTIVITY_TYPE) { type = NavType.StringType })
            ) { backStackEntry ->
                val typeString = backStackEntry.arguments?.getString(NavGroup.ACTIVITY_TYPE).orEmpty()
                val activityType = ActivityType.valueOf(typeString)
                UserActivityScreen(activityType = activityType, navController = navController, innerPadding = innerPadding)
            }

            composable(
                route = NavGroup.QNA_WITH_CATEGORY,
                arguments = listOf(
                    navArgument("category") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val categoryString =
                    backStackEntry.arguments?.getString("category")!!

                val category = Category.valueOf(categoryString)

                QnaScreen(
                    navController = navController,
                    innerPadding = innerPadding,
                    initialCategory = category,
                    authViewModel
                )
            }

            composable(
                route = "write/{postId}?title={title}&content={content}&category={category}&author={author}",                arguments = listOf(
                    navArgument("postId") { type = NavType.IntType; defaultValue = 0 },
                    navArgument("title") { type = NavType.StringType; defaultValue = "" },
                    navArgument("content") { type = NavType.StringType; defaultValue = "" },
                    navArgument("category") { type = NavType.StringType; defaultValue = "" },
                    navArgument("author") { type = NavType.StringType; defaultValue = "" }
                )
            ) { backStackEntry ->

                val postId = backStackEntry.arguments?.getInt("postId") ?: 0
                val title = backStackEntry.arguments?.getString("title")
                val content = backStackEntry.arguments?.getString("content")
                val categoryName = backStackEntry.arguments?.getString("category")
                val author = backStackEntry.arguments?.getString("author")
                val category = Category.entries.firstOrNull { it.name == categoryName }

                WriteScreen(
                    navController = navController,
                    innerPadding = innerPadding,
                    postId = postId,
                    initialTitle = title,
                    initialContent = content,
                    initialCategory = category,
                    initialAuthor = author
                )
            }
        }
    }
}
