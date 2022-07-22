package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NonFillingVerticalSpacer(
    height: Dp = 100.dp,
) {
    Spacer(
        modifier = Modifier
            .height(
                height = height,
            ),
    )
}

@Composable
fun VerticalSpacer(
    height: Dp = 100.dp,
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = height,
            ),
    )
}

@Composable
fun HorizontalSpacer(
    width: Dp = 100.dp,
) {
    Spacer(
        modifier = Modifier
            .width(
                width = width,
            ),
    )
}
