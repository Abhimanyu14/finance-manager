package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager

@Composable
fun MyTopAppBar(
    navigationManager: NavigationManager,
    @StringRes titleTextStringResourceId: Int,
    isNavigationIconVisible: Boolean,
) {
    MyTopAppBarView(
        navigationManager = navigationManager,
        titleText = stringResource(
            id = titleTextStringResourceId,
        ),
        isNavigationIconVisible = isNavigationIconVisible,
    )
}

@Composable
private fun MyTopAppBarView(
    navigationManager: NavigationManager,
    titleText: String,
    isNavigationIconVisible: Boolean,
) {
    CenterAlignedTopAppBar(
        title = {
            MyText(
                text = titleText,
                style = TextStyle(
                    color = Primary,
                ),
            )
        },
        navigationIcon = {
            if (isNavigationIconVisible) {
                NavigationBackButton(
                    navigationManager = navigationManager,
                )
            }
        },
        modifier = Modifier
            .background(
                color = Surface,
            ),
    )
}
