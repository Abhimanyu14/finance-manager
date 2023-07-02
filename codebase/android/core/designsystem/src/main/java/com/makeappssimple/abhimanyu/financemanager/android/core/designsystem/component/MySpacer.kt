package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NonFillingVerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = 100.dp,
) {
    Spacer(
        modifier = modifier
            .height(
                height = height,
            ),
    )
}

@Composable
fun VerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = 100.dp,
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = height,
            ),
    )
}

@Composable
fun NavigationBarSpacer() {
    VerticalSpacer(
        modifier = Modifier
            .navigationBarsPadding(),
        height = 0.dp,
    )
}

@Composable
fun HorizontalSpacer(
    modifier: Modifier = Modifier,
    width: Dp = 100.dp,
) {
    Spacer(
        modifier = modifier
            .width(
                width = width,
            ),
    )
}
