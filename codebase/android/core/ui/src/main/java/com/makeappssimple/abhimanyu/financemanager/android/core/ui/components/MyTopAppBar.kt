package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
fun MyTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleTextStringResourceId: Int,
    navigationAction: (() -> Unit)? = null,
) {
    MyTopAppBarView(
        isNavigationIconVisible = navigationAction.isNotNull(),
        titleText = stringResource(
            id = titleTextStringResourceId,
        ),
        modifier = modifier,
    ) {
        MyNavigationBackButton(
            onClick = {
                navigationAction?.invoke()
            },
        )
    }
}

@Composable
fun MyTopAppBarView(
    modifier: Modifier = Modifier,
    isNavigationIconVisible: Boolean,
    titleText: String,
    navigationBackButton: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            MyText(
                text = titleText,
                style = MaterialTheme.typography.titleLarge
                    .copy(
                        color = MaterialTheme.colorScheme.primary,
                    ),
            )
        },
        navigationIcon = {
            if (isNavigationIconVisible) {
                navigationBackButton()
            }
        },
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            ),
    )
}
