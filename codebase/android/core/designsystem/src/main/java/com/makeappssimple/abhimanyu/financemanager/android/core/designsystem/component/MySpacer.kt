package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areNavigationBarsVisible
import androidx.compose.foundation.layout.areStatusBarsVisible
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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

@Composable
fun NavigationBarSpacer() {
    Spacer(
        modifier = Modifier.navigationBarSpacer(),
    )
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.navigationBarSpacer(): Modifier {
    // return this

    //*
    return composed {
        if (WindowInsets.areNavigationBarsVisible) {
            this
                .padding(
                    bottom = navigationBarHeight(),
                )

//            Modifier.windowInsetsBottomHeight(
//                insets = WindowInsets.safeDrawing,
//            )
        } else {
            this
        }
    }
    // */
}

@Composable
fun navigationBarHeight(): Dp {
    return 0.dp // WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}


@Composable
fun StatusBarSpacer() {
    Spacer(
        modifier = Modifier.statusBarSpacer(),
    )
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.statusBarSpacer(): Modifier {
    return composed {
        if (WindowInsets.areStatusBarsVisible) {
            this
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                )
        } else {
            this
        }
    }
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
