package com.linkup.android.ui.components

import androidx.annotation.Size
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ThumbUp(
    backgroundColor: Color,
    mainColor: Color,
    size: Dp = 60.dp,
    onClick: () -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "buttonScale"
    )

    Box(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .background(color = backgroundColor, shape = CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.ThumbUp,
            contentDescription = "Thumb Up",
            tint = mainColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}