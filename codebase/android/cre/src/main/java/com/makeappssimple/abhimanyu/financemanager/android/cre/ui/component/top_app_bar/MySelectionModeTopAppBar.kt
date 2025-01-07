package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.top_app_bar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.cre.R
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.typealiases.RowScopedComposableContent

@Composable
public fun MySelectionModeTopAppBar(
    modifier: Modifier = Modifier,
    appBarActions: RowScopedComposableContent = {},
    navigationAction: () -> Unit = {},
    title: ComposableContent = {},
) {
    MySelectionModeTopAppBarUI(
        modifier = modifier,
        appBarActions = appBarActions,
        navigationAction = navigationAction,
        title = title,
    )
}

@Composable
public fun MySelectionModeTopAppBarUI(
    modifier: Modifier = Modifier,
    appBarActions: RowScopedComposableContent,
    navigationAction: () -> Unit,
    title: ComposableContent,
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            MyIconButton(
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = MyIcons.Close,
                contentDescriptionStringResourceId = R.string.navigation_close_button_navigation_icon_content_description,
                onClick = navigationAction,
            )
        },
        actions = appBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier,
    )
}
