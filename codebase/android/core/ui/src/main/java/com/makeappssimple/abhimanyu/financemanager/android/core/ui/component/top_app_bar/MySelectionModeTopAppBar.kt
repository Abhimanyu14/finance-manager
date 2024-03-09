package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons

@Composable
fun MySelectionModeTopAppBar(
    modifier: Modifier = Modifier,
    appBarActions: @Composable RowScope.() -> Unit = {},
    navigationAction: () -> Unit = {},
    title: @Composable () -> Unit = {},
) {
    MySelectionModeTopAppBarUI(
        modifier = modifier,
        appBarActions = appBarActions,
        navigationAction = navigationAction,
        title = title,
    )
}

@Composable
fun MySelectionModeTopAppBarUI(
    modifier: Modifier = Modifier,
    appBarActions: @Composable RowScope.() -> Unit,
    navigationAction: () -> Unit,
    title: @Composable () -> Unit,
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            IconButton(
                onClick = navigationAction,
            ) {
                Icon(
                    imageVector = MyIcons.Close,
                    contentDescription = stringResource(
                        id = R.string.navigation_close_button_navigation_icon_content_description,
                    ),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        actions = appBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier,
    )
}
