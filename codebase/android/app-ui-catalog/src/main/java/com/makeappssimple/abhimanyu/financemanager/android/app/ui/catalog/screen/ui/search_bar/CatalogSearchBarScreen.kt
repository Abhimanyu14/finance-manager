package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.search_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.searchbar.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.searchbar.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.searchbar.MySearchBarEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
internal fun CatalogSearchBarScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        coroutineScope = coroutineScope,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_search_bar,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val (searchText, setSearchText) = remember {
                mutableStateOf("")
            }
            MySearchBar(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
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
            MySearchBar(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                data = MySearchBarData(
                    isLoading = true,
                ),
            )
        }
    }
}
