package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.top_app_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.navigation_back_button.MyNavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBarUI

@Composable
public fun CatalogTopAppBarScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        coroutineScope = coroutineScope,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_top_app_bar,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyTopAppBarUI(
                isNavigationIconVisible = true,
                titleText = "Title",
                appBarActions = {},
                navigationBackButton = {
                    MyNavigationBackButton()
                },
            )
            MyTopAppBarUI(
                isNavigationIconVisible = false,
                titleText = "Title",
                appBarActions = {},
                navigationBackButton = {
                    MyNavigationBackButton()
                },
            )
        }
    }
}
