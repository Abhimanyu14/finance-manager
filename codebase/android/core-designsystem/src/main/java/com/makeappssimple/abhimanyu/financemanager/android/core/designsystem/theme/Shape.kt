package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

internal val Shapes = Shapes(
    small = RoundedCornerShape(
        size = 4.dp,
    ),
    medium = RoundedCornerShape(
        size = 4.dp,
    ),
    large = RoundedCornerShape(
        size = 0.dp,
    )
)

val BottomSheetExpandedShape = RectangleShape

val BottomSheetShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)
