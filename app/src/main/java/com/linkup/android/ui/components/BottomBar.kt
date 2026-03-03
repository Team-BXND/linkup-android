package com.linkup.android.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.linkup.android.feature.auth.AuthViewModel
import com.linkup.android.root.NavGroup

@Composable
fun BottomBar(
    navController: NavController,
    authViewModel: AuthViewModel? = null
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isLoggedIn = authViewModel?.isLoggedIn

    NavigationBar(
        modifier = Modifier
            .height(64.dp),
                windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        NavigationBarItem(
            selected = currentRoute == NavGroup.HOME,
            onClick = {
                navController.navigate(NavGroup.HOME) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == NavGroup.QNA,
            onClick = { navController.navigate(NavGroup.QNA) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                    contentDescription = "QnA"
                )
            },
            label = { Text("QnA") }
        )
        NavigationBarItem(
            selected = currentRoute == NavGroup.RANK,
            onClick = {
                navController.navigate(NavGroup.RANK) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Rank"

                )
            } ,
            label = {Text("Rank")}
        )

        NavigationBarItem(
            selected = currentRoute == NavGroup.PROFILE,
            onClick = {
                if (isLoggedIn == true) {
                    navController.navigate(NavGroup.PROFILE) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(NavGroup.MOVETOAUTH) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "profile"
                )
            },
            label = {Text("Profile")}
        )
    }
}
