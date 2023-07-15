package com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.ifTrue
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.shimmer(
    isShimmerVisible: Boolean = true,
    shape: Shape? = null,
    backgroundColor: Color? = null,
    shimmerColor: Color? = null,
): Modifier {
    return composed {
        var width by remember {
            mutableFloatStateOf(0f)
        }
        var height by remember {
            mutableFloatStateOf(0f)
        }
        val targetValue = 3 * sqrt((height.pow(2) + width.pow(2)))
        val brush = if (backgroundColor.isNotNull() && shimmerColor.isNotNull()) {
            shimmerBrush(
                targetValue = targetValue,
                isShimmerVisible = isShimmerVisible,
                backgroundColor = backgroundColor,
                shimmerColor = shimmerColor,
            )
        } else if (backgroundColor.isNotNull()) {
            shimmerBrush(
                targetValue = targetValue,
                isShimmerVisible = isShimmerVisible,
                backgroundColor = backgroundColor,
            )
        } else {
            shimmerBrush(
                targetValue = targetValue,
                isShimmerVisible = isShimmerVisible,
            )
        }

        this.then(
            other = Modifier
                .onSizeChanged {
                    width = it.width.toFloat()
                    height = it.height.toFloat()
                }
                .background(
                    brush = brush,
                )
                .ifTrue(shape.isNotNull()) {
                    shape?.let {
                        this.clip(
                            shape = it,
                        )
                    } ?: this
                },
        )
    }
}

@Composable
fun shimmerBrush(
    isShimmerVisible: Boolean = true,
    targetValue: Float,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    shimmerColor: Color = MaterialTheme.colorScheme.primaryContainer,
): Brush {
    return if (isShimmerVisible) {
        val shimmerColors = listOf(
            backgroundColor.copy(
                alpha = 0.4F,
            ),
            shimmerColor.copy(
                alpha = 0.8F,
            ),
            backgroundColor.copy(
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
                    durationMillis = 1500,
                    delayMillis = 100,
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
