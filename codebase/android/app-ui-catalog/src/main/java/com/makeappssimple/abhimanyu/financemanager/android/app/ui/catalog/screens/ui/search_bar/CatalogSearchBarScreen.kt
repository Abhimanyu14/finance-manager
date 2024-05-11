package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.search_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
public fun CatalogSearchBarScreen(
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
                titleTextStringResourceId = R.string.screen_search_bar,
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
            val (searchText, setSearchText) = remember {
                mutableStateOf("")
            }
            MySearchBarContainer {
                MySearchBar(
                    data = MySearchBarData(
                        autoFocus = false,
                        placeholderText = "Placeholder",
                        searchText = searchText,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MySearchBarEvent.OnSearch -> {}

                            is MySearchBarEvent.OnSearchTextChange -> {
                                setSearchText(event.updatedSearchText)
                            }
                        }
                    },
                )
            }
        }
    }
}
