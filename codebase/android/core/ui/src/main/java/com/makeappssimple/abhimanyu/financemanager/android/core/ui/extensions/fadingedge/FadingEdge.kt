package com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.fadingedge

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero

private object FadingEdgeConstants {
    const val DEFAULT_PERCENTAGE = 0.1F
    val defaultStart = 8.dp
    val defaultEnd = 8.dp
}

public fun Modifier.fadingEdge(
    start: Dp = FadingEdgeConstants.defaultStart,
    end: Dp = FadingEdgeConstants.defaultEnd,
): Modifier {
    return composed {
        val density = LocalDensity.current
        this
            .graphicsLayer(
                compositingStrategy = CompositingStrategy.Offscreen,
            )
            .drawWithContent {
                drawContent()
                val widthInDp = with(
                    receiver = density,
                ) {
                    size.width.toDp()
                }
                val startPercentage = if (size.width.isNotZero()) {
                    start / widthInDp
                } else {
                    FadingEdgeConstants.DEFAULT_PERCENTAGE
                }
                val endPercentage = if (size.width.isNotZero()) {
                    end / widthInDp
                } else {
                    FadingEdgeConstants.DEFAULT_PERCENTAGE
                }
                val brush = Brush.horizontalGradient(
                    0F to Color.Transparent,
                    startPercentage to Color.Black,
                    (1F - endPercentage) to Color.Black,
                    1F to Color.Transparent
                )
                drawRect(
                    brush = brush,
                    blendMode = BlendMode.DstIn,
                )
            }
    }
}
