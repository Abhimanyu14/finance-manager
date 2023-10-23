package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areStatusBarsVisible
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.ifTrue

private val defaultSpacerSize = 100.dp

@Composable
fun HorizontalSpacer(
    modifier: Modifier = Modifier,
    width: Dp = defaultSpacerSize,
) {
    Spacer(
        modifier = modifier
            .width(
                width = width,
            ),
    )
}

@Composable
fun VerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = defaultSpacerSize,
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
fun NonFillingVerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = defaultSpacerSize,
) {
    Spacer(
        modifier = modifier
            .height(
                height = height,
            ),
    )
}

@Composable
fun StatusBarSpacer() {
    Spacer(
        modifier = Modifier
            .statusBarSpacer(),
    )
}

@Composable
fun NavigationBarsAndImeSpacer() {
    Spacer(
        modifier = Modifier
            .navigationBarsSpacer()
            .imeSpacer(),
    )
}

@Composable
fun NavigationBarsSpacer() {
    Spacer(
        modifier = Modifier
            .navigationBarsSpacer(),
    )
}

@Composable
fun ImeSpacer() {
    Spacer(
        modifier = Modifier
            .imeSpacer(),
    )
}

fun Modifier.navigationBarLandscapeSpacer(): Modifier {
    return composed {
        Modifier
            .fillMaxSize()
            .ifTrue(
                condition = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ) {
                this.navigationBarsSpacer()
            }
    }
}

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

fun Modifier.navigationBarsSpacer(): Modifier {
    return this.navigationBarsPadding()
}

fun Modifier.imeSpacer(): Modifier {
    return this.imePadding()
}

@Composable
fun navigationBarHeight(): Dp {
    return 0.dp // WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}
