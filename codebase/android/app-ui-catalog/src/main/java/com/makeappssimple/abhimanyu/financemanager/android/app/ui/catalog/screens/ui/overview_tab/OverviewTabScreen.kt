package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.overview_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTab
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold

@Composable
fun OverviewTabScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_overview_tab,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                )
                .clip(
                    shape = MaterialTheme.shapes.medium,
                )
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                .padding(
                    all = 16.dp,
                ),
        ) {
            var selectedIndex by remember {
                mutableIntStateOf(0)
            }
            OverviewTab(
                data = OverviewTabData(
                    items = OverviewTabOption.values()
                        .map {
                            it.title
                        },
                    selectedItemIndex = selectedIndex,
                ),
                events = OverviewTabEvents(
                    onClick = {
                        selectedIndex = it
                    },
                ),
            )
        }
    }
}
