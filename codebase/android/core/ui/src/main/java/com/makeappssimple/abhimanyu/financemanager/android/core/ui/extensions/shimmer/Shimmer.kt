package com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import kotlin.math.pow
import kotlin.math.sqrt

private const val DEFAULT_SHIMMER_DURATION = 1500
private const val DEFAULT_SHIMMER_DELAY = 100

fun Modifier.shimmer(
    isShimmerVisible: Boolean = true,
    backgroundColor: Color? = null,
    shimmerColor: Color? = null,
): Modifier {
    Log.e("Abhi", "shimmer")
    return composed {
        Log.e("Abhi", "shimmer composed")
        var targetValue by remember {
            mutableFloatStateOf(0F)
        }
        val brush by rememberShimmerBrush(
            isShimmerVisible = isShimmerVisible,
            targetValue = targetValue,
            backgroundColor = backgroundColor,
            shimmerColor = shimmerColor,
        )

        this.then(
            other = Modifier
                .onSizeChanged {
                    val newTargetValue =
                        3 * sqrt(
                            it.height
                                .toFloat()
                                .pow(2) + it.width
                                .toFloat()
                                .pow(2)
                        )
                    if (targetValue != newTargetValue) {
                        targetValue = newTargetValue
                    }
                }
                .drawBehind {
                    drawRect(
                        brush = brush,
                    )
                }
        )
    }
}

@Composable
fun rememberShimmerBrush(
    isShimmerVisible: Boolean = true,
    targetValue: Float,
    backgroundColor: Color? = MaterialTheme.colorScheme.surfaceVariant,
    shimmerColor: Color? = MaterialTheme.colorScheme.primaryContainer,
): State<Brush> {
    Log.e("Abhi", "shimmerBrush")
    val shimmerColors = listOf(
        (backgroundColor ?: MaterialTheme.colorScheme.surfaceVariant).copy(
            alpha = 0.4F,
        ),
        (shimmerColor ?: MaterialTheme.colorScheme.primaryContainer).copy(
            alpha = 0.8F,
        ),
        (backgroundColor ?: MaterialTheme.colorScheme.surfaceVariant).copy(
            alpha = 0.4F,
        ),
    )
    val transition = rememberInfiniteTransition(
        label = "shimmer_transition",
    )
    val translateAnimation = transition.animateFloat(
        initialValue = 0F,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = DEFAULT_SHIMMER_DURATION,
                delayMillis = DEFAULT_SHIMMER_DELAY,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_translation",
    )
    return remember {
        derivedStateOf {
            if (isShimmerVisible) {
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
    }
}
