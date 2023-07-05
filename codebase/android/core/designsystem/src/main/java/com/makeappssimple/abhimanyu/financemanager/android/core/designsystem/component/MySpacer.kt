package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areNavigationBarsVisible
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationBarSpacer() {
    Log.e("Abhi", "${WindowInsets.statusBars.asPaddingValues().calculateTopPadding()}")
    Log.e("Abhi", "${WindowInsets.navigationBars.asPaddingValues().calculateTopPadding()}")
    Log.e("Abhi", "${WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()}")
    Log.e("Abhi", "${WindowInsets.areNavigationBarsVisible}")
    Spacer(
        modifier = if (WindowInsets.areNavigationBarsVisible) {
            Modifier.navigationBarsPadding()

//            .padding(
//                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
//            )

//            Modifier.windowInsetsBottomHeight(
//                insets = WindowInsets.safeDrawing,
//            )
        } else {
            Modifier
        },
    )
}

@Composable
fun StatusBarSpacer() {
    Spacer(
        modifier = Modifier.windowInsetsTopHeight(
            insets = WindowInsets.safeDrawing,
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
