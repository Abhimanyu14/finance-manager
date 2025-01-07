package com.makeappssimple.abhimanyu.financemanager.android.cre.chart.composepie.legend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
public fun Dot(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .requiredSize(
                size = 8.dp,
            )
            .background(
                color = color,
                shape = CircleShape,
            ),
    )
}
