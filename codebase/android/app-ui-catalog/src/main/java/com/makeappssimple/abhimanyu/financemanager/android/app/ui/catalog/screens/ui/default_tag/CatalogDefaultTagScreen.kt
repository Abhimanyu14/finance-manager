package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.default_tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.default_tag.MyDefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
public fun CatalogDefaultTagScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onNavigationBackButtonClick = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_default_tag,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(
                    space = 8.dp,
                ),
        ) {
            MyDefaultTag()
        }
    }
}
