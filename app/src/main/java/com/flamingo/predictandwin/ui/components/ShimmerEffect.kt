package com.flamingo.predictandwin.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.flamingo.predictandwin.ui.theme.SurfaceCard
import com.flamingo.predictandwin.ui.theme.SurfaceCardElevated
import com.flamingo.predictandwin.ui.theme.SurfaceOverlay

/**
 * A reusable shimmer effect modifier for loading/skeleton states.
 *
 * Apply this to any composable to show a subtle, animated shine
 * that sweeps from left to right, indicating content is loading.
 *
 * Usage:
 * ```
 * Box(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(48.dp)
 *         .clip(RoundedCornerShape(12.dp))
 *         .shimmerEffect()
 * )
 * ```
 */
fun Modifier.shimmerEffect(
    durationMillis: Int = 1200,
    baseColor: Color = SurfaceCard,
    highlightColor: Color = SurfaceOverlay,
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateX by transition.animateFloat(
        initialValue = -300f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_x",
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            baseColor,
            highlightColor,
            baseColor,
        ),
        start = Offset(translateX, 0f),
        end = Offset(translateX + 300f, 0f),
    )

    this.background(shimmerBrush)
}
