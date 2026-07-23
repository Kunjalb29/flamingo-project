package com.flamingo.predictandwin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flamingo.predictandwin.ui.theme.GoldDark
import com.flamingo.predictandwin.ui.theme.GoldPrimary

/**
 * A horizontal gradient divider that fades from transparent → gold → transparent.
 * More visually appealing than a plain [HorizontalDivider] for section separation.
 *
 * @param modifier Modifier for additional styling.
 * @param thickness Height of the divider line.
 * @param horizontalPadding Padding on each side.
 * @param centerColor The peak color at the center of the gradient.
 */
@Composable
fun GradientDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    horizontalPadding: Dp = 32.dp,
    centerColor: Color = GoldPrimary.copy(alpha = 0.3f),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .height(thickness)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        centerColor,
                        Color.Transparent,
                    ),
                ),
            ),
    )
}
