package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.navigation.CatalogScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemSectionTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemSectionTitleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
public fun CatalogHomeScreen(
    navigateTo: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onNavigationBackButtonClick = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_home_appbar_title,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                vertical = 16.dp,
            ),
        ) {
            item {
                MyListItemSectionTitle(
                    data = MyListItemSectionTitleData(
                        text = "Foundation",
                    ),
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_color,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.Color.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_text,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.Text.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItemSectionTitle(
                    data = MyListItemSectionTitleData(
                        text = "Components",
                    ),
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_default_tag,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.DefaultTag.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_emoji_circle,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.EmojiCircle.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_navigation_back_button,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.NavigationBackButton.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_outlined_text_field,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.OutlinedTextField.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_overview_card,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.OverviewCard.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_overview_tab,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.OverviewTab.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_save_button,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.SaveButton.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_search_bar,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.SearchBar.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_selection_group,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.SelectionGroup.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_top_app_bar,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.TopAppBar.route)
                            }
                        }
                    },
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_total_balance_card,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyListItemEvent.OnClick -> {
                                navigateTo(CatalogScreen.TotalBalanceCard.route)
                            }
                        }
                    },
                )
            }
        }
    }
}
