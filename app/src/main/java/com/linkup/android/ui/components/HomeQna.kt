package com.linkup.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linkup.android.ui.theme.MainColor
import com.linkup.android.ui.theme.SubColor

@Composable
fun HomeQna (
    rank: Int,
    title: String,
    author: String,
    like: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val borderColor = if (isSelected) SubColor else Color.LightGray

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 26.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "${rank}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainColor
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {

                Text(
                    title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row {
                    Text(
                        "${author} 님",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.padding(start = 6.dp))

                    Text(
                        "유용해요 ${like}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

            }
        }
    }
}