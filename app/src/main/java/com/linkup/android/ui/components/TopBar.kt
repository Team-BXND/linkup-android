package com.linkup.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
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
import com.linkup.android.ui.theme.MainColor

@Composable
fun TopBar(navController: NavController.Companion){
    Row(
        modifier = Modifier
            .padding(horizontal = 13.dp)
            .padding(top = 53.dp)
            .fillMaxWidth()
            .height(53.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "LINK:UP",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor
        )
    }
}