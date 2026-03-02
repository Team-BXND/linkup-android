package com.linkup.android.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.linkup.android.feature.auth.AuthViewModel
import com.linkup.android.root.NavGroup
import androidx.compose.runtime.collectAsState // Flow를 State로 변환
import androidx.compose.runtime.getValue       // 'by' 키워드 사용 가능하게 함
@Composable
fun BottomBar(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isLoggedIn = authViewModel.isLoggedIn

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
                if (isLoggedIn) {
                    navController.navigate(NavGroup.PROFILE) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                } else { // 여기에 } 가 추가되어야 합니다.
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


//        NavigationBarItem(
//            selected = currentRoute == NavGroup,
//            onClick = { navController.navigate(NavGroup) },
//            icon = {
//                Icon(
//                    imageVector = Icons.Outlined.QuestionMark,
//                    contentDescription = "QnA"
//                )
//            },
//            label = { Text("QnA") }
//        )

//        NavigationBarItem(
//            selected = currentRoute == NavGroup.Rank
//            onClick = { navController.navigate(NavGroup)},
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.BarChart,
//                    contentDescription = "랭킹")
//            },
//            label = { Text("Rank") }
//        )
//    }
    }
}
