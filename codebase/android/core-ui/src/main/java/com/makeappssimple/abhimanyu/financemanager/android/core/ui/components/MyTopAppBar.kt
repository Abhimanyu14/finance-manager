package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
fun MyTopAppBar(
    @StringRes titleTextStringResourceId: Int,
    navigationAction: (() -> Unit)? = null,
) {
    MyTopAppBarView(
        titleText = stringResource(
            id = titleTextStringResourceId,
        ),
        isNavigationIconVisible = navigationAction != null,
    ) {
        NavigationBackButton(
            onClick = {
                navigationAction?.invoke()
            },
        )
    }
}

@Composable
fun MyTopAppBarView(
    titleText: String,
    isNavigationIconVisible: Boolean,
    navigationBackButton: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            MyText(
                text = titleText,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        navigationIcon = {
            if (isNavigationIconVisible) {
                navigationBackButton()
            }
        },
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
            ),
    )
}
