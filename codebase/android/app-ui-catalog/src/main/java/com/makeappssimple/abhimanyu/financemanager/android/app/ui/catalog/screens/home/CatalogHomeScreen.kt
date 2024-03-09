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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemSectionTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common.MyListItemSectionTitleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
fun CatalogHomeScreen(
    navigateTo: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
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
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.Color.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_text,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.Text.route)
                        },
                    )
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
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.DefaultTag.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_emoji_circle,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.EmojiCircle.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_navigation_back_button,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.NavigationBackButton.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_outlined_text_field,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.OutlinedTextField.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_overview_card,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.OverviewCard.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_overview_tab,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.OverviewTab.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_save_button,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.SaveButton.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_search_bar,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.SearchBar.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_selection_group,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.SelectionGroup.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_top_app_bar,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.TopAppBar.route)
                        },
                    )
                )
            }
            item {
                MyListItem(
                    data = MyListItemData(
                        textStringResourceId = R.string.screen_total_balance_card,
                    ),
                    events = MyListItemEvents(
                        onClick = {
                            navigateTo(CatalogScreen.TotalBalanceCard.route)
                        },
                    )
                )
            }
        }
    }
}
