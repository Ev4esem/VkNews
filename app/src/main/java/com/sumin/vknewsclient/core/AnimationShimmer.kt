package com.sumin.vknewsclient.core

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.shimmerLoadingAnimation(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2.0f,
        targetValue = 2.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = ""
    )

    this.drawWithCache {
        val offsetX = this.size.width * startOffsetX
        val gradient = listOf(
            Color(0xFFB8B5B5),
            Color(0xFF8F8B8B),
            Color(0xFFB8B5B5),
        )

        onDrawWithContent {
            drawContent()
            drawRect(
                brush = Brush.linearGradient(
                    gradient,
                    start = Offset(offsetX, y = 0f),
                    end = Offset(offsetX + size.width, size.height),
                ),
            )
        }
    }
}
