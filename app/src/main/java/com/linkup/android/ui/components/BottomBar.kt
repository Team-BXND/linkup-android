package com.linkup.android.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
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
import com.linkup.android.root.NavGroup

@Composable
fun BottomBar(
    navController: NavController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    NavigationBar(
        modifier = Modifier.height(64.dp),
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
            onClick = { navController.navigate(NavGroup.RANK)},
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "랭킹")
            },
            label = { Text("Rank") }
        )
    }
}
