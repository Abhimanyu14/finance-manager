package com.makeappssimple.abhimanyu.financemanager.android.core.ui.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.shimmer(
    isShimmerVisible: Boolean = true,
): Modifier {
    return composed {
        val configuration = LocalConfiguration.current
        val screenWidthInPx = with(LocalDensity.current) {
            configuration.screenWidthDp.dp.toPx()
        }
        val screenHeightInPx = with(LocalDensity.current) {
            configuration.screenHeightDp.dp.toPx()
        }
        val targetValue = 3 * sqrt((screenHeightInPx.pow(2) + screenWidthInPx.pow(2)))

        this.then(
            other = Modifier
                .background(
                    brush = shimmerBrush(
                        targetValue = targetValue,
                        isShimmerVisible = isShimmerVisible,
                    ),
                ),
        )
    }
}

@Composable
fun shimmerBrush(
    isShimmerVisible: Boolean = true,
    targetValue: Float = 1000F,
): Brush {
    return if (isShimmerVisible) {
        val shimmerColors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.4F,
            ),
            MaterialTheme.colorScheme.primaryContainer.copy(
                alpha = 0.8F,
            ),
            MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.4F,
            ),
        )

        val transition = rememberInfiniteTransition(
            label = "",
        )
        val translateAnimation = transition.animateFloat(
            initialValue = 0F,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                    delayMillis = 300,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "",
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(
                x = translateAnimation.value,
                y = translateAnimation.value,
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent,
            ),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}
