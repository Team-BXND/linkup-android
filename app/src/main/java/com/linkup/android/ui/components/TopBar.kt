package com.linkup.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.linkup.android.R
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.theme.MainColor

@Composable
fun TopBar(navController: NavController){
    Row(
        modifier = Modifier
            .padding(horizontal = 13.dp)
            .padding(top = 53.dp)
            .fillMaxWidth()
            .height(53.dp)
            .clickable(
                onClick = { navController.navigate(NavGroup.HOME) }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxHeight()
        )
        Text(
            text = "LINK:UP",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MainColor,
            modifier = Modifier
                .fillMaxHeight()
        )
    }
}