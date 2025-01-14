package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
public fun Dp.dpToPx(): Float = with(
    receiver = LocalDensity.current,
) {
    this@dpToPx.toPx()
}

@Composable
public fun Int.pxToDp(): Dp = with(
    receiver = LocalDensity.current,
) {
    this@pxToDp.toDp()
}
