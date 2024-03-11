package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.NullableComposableContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.navigation_back_button.MyNavigationBackButton

@Composable
fun MyTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleTextStringResourceId: Int,
    navigationAction: (() -> Unit)? = null,
    appBarActions: NullableComposableContent = null,
) {
    MyTopAppBarUI(
        isNavigationIconVisible = navigationAction.isNotNull(),
        titleText = stringResource(
            id = titleTextStringResourceId,
        ),
        modifier = modifier,
        appBarActions = appBarActions,
        navigationBackButton = {
            MyNavigationBackButton(
                onClick = {
                    navigationAction?.invoke()
                },
            )
        },
    )
}

@Composable
fun MyTopAppBarUI(
    modifier: Modifier = Modifier,
    isNavigationIconVisible: Boolean,
    titleText: String,
    appBarActions: NullableComposableContent,
    navigationBackButton: ComposableContent,
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
        actions = {
            appBarActions?.invoke()
        },
        colors = TopAppBarDefaults
            .centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        modifier = modifier,
    )
}
