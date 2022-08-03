package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Error
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Green700
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.LightGray
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.DefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyHorizontalScrollingSuggestionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBarView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.NavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCardView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCardViewData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.SearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.SearchBarContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.SearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCardView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCardViewData

@Composable
fun MyAppCatalog() {
    MyAppTheme {
        Surface {
            val contentPadding = WindowInsets
                .systemBars
                .add(
                    WindowInsets(
                        left = 16.dp,
                        top = 16.dp,
                        right = 16.dp,
                        bottom = 16.dp,
                    )
                )
                .asPaddingValues()
            CatalogList(
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
private fun CatalogList(
    contentPadding: PaddingValues,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
        ),
    ) {
        item {
            MyText(
                text = "UI Catalog",
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Text",
            )
        }
        item {
            Column {
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Display Large",
                    style = MaterialTheme.typography.displayLarge,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Display Medium",
                    style = MaterialTheme.typography.displayMedium,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Display Small",
                    style = MaterialTheme.typography.displaySmall,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Headline Large",
                    style = MaterialTheme.typography.headlineLarge,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Headline Medium",
                    style = MaterialTheme.typography.headlineMedium,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Headline Small",
                    style = MaterialTheme.typography.headlineSmall,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Title Large",
                    style = MaterialTheme.typography.titleLarge,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Title Medium",
                    style = MaterialTheme.typography.titleMedium,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Title Small",
                    style = MaterialTheme.typography.titleSmall,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Body Large",
                    style = MaterialTheme.typography.bodyLarge,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Body Medium",
                    style = MaterialTheme.typography.bodyMedium,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Body Small",
                    style = MaterialTheme.typography.bodySmall,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Label Large",
                    style = MaterialTheme.typography.labelLarge,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Label Medium",
                    style = MaterialTheme.typography.labelMedium,
                )
                MyText(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                    text = "Label Small",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Selection Group",
            )
        }
        item {
            val items = listOf(
                ChipItem(
                    text = "Item 1",
                ),
                ChipItem(
                    text = "Item 2",
                ),
                ChipItem(
                    text = "Item 3",
                ),
                ChipItem(
                    text = "Item 4",
                ),
                ChipItem(
                    text = "Item 5",
                ),
                ChipItem(
                    text = "Item 6",
                ),
            )
            val selectedItemsIndices = remember {
                mutableStateListOf<Int>()
            }
            MySelectionGroup(
                items = items,
                selectedItemsIndices = selectedItemsIndices,
                onSelectionChange = {
                    if (selectedItemsIndices.contains(it)) {
                        selectedItemsIndices.remove(it)
                    } else {
                        selectedItemsIndices.add(it)
                    }
                },
            )
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Radio Group",
            )
        }
        item {
            val items = listOf(
                ChipItem(
                    text = "Item 1",
                ),
                ChipItem(
                    text = "Item 2",
                ),
                ChipItem(
                    text = "Item 3",
                ),
                ChipItem(
                    text = "Item 4",
                ),
                ChipItem(
                    text = "Item 5",
                ),
                ChipItem(
                    text = "Item 6",
                ),
            )
            var selectedItemIndex by remember {
                mutableStateOf(0)
            }
            MyRadioGroup(
                items = items,
                selectedItemIndex = selectedItemIndex,
                onSelectionChange = {
                    selectedItemIndex = it
                },
            )
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Horizontal Scrolling Radio Group",
            )
        }
        item {
            val items = listOf(
                ChipItem(
                    text = "Item 1",
                ),
                ChipItem(
                    text = "Item 2",
                ),
                ChipItem(
                    text = "Item 3",
                ),
                ChipItem(
                    text = "Item 4",
                ),
                ChipItem(
                    text = "Item 5",
                ),
                ChipItem(
                    text = "Item 6",
                ),
            )
            var selectedItemIndex by remember {
                mutableStateOf(0)
            }
            MyHorizontalScrollingRadioGroup(
                items = items,
                selectedItemIndex = selectedItemIndex,
                onSelectionChange = {
                    selectedItemIndex = it
                },
            )
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Horizontal Scrolling Suggestion Group",
            )
        }
        item {
            val items = listOf(
                ChipItem(
                    text = "Item 1",
                ),
                ChipItem(
                    text = "Item 2",
                ),
                ChipItem(
                    text = "Item 3",
                ),
                ChipItem(
                    text = "Item 4",
                ),
                ChipItem(
                    text = "Item 5",
                ),
                ChipItem(
                    text = "Item 6",
                ),
            )
            MyHorizontalScrollingSuggestionGroup(
                items = items,
                onSelectionChange = {
                    Toast.makeText(context, "Selected ${items[it].text}", Toast.LENGTH_SHORT).show()
                },
            )
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Total Balance Card",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                TotalBalanceCardView(
                    data = TotalBalanceCardViewData(
                        totalBalanceAmount = 1234567890,
                    ),
                )
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Overview Card",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                OverviewCardView(
                    data = OverviewCardViewData(
                        onClick = {},
                        overviewTabSelectionIndex = 1,
                        onOverviewTabClick = {},
                        pieChartData = PieChartData(
                            items = listOf(
                                PieChartItemData(
                                    value = 500F,
                                    text = "Income : ₹500",
                                    color = Green700,
                                ),
                                PieChartItemData(
                                    value = 600F,
                                    text = "Expense : ₹600",
                                    color = Error,
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Default Tag",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                DefaultTag()
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Emoji Circle",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                EmojiCircle(
                    emoji = "😀",
                )
                EmojiCircle(
                    emoji = "😀",
                    emojiCircleSize = EmojiCircleSize.Normal,
                )
                EmojiCircle(
                    emoji = "😀",
                    backgroundColor = LightGray,
                )
                EmojiCircle(
                    emoji = "😀",
                    backgroundColor = LightGray,
                    emojiCircleSize = EmojiCircleSize.Normal,
                )
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Save Button",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                SaveButton(
                    textStringResourceId = R.string.save_button,
                    isEnabled = false,
                    onClick = {},
                )
                SaveButton(
                    textStringResourceId = R.string.save_button,
                    isEnabled = true,
                    onClick = {},
                )
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Search Bar",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                val (searchText, setSearchText) = remember {
                    mutableStateOf("")
                }
                SearchBarContainer {
                    SearchBar(
                        data = SearchBarData(
                            autoFocus = false,
                            searchText = searchText,
                            placeholderText = "Placeholder",
                            onValueChange = setSearchText,
                        ),
                    )
                }
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "My Search Bar",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                val (searchText, setSearchText) = remember {
                    mutableStateOf("")
                }
                SearchBarContainer {
                    MySearchBar(
                        data = SearchBarData(
                            autoFocus = false,
                            searchText = searchText,
                            placeholderText = "Placeholder",
                            onValueChange = setSearchText,
                        ),
                    )
                }
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "My Top App Bar",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                MyTopAppBarView(
                    titleText = "Title",
                    isNavigationIconVisible = true,
                    navigationBackButton = {
                        NavigationBackButton(
                            onClick = {},
                        )
                    },
                )
                MyTopAppBarView(
                    titleText = "Title",
                    isNavigationIconVisible = false,
                    navigationBackButton = {
                        NavigationBackButton(
                            onClick = {},
                        )
                    },
                )
            }
        }

        item {
            MyText(
                modifier = Modifier.padding(
                    top = 16.dp,
                ),
                text = "Navigation Back Button",
            )
        }
        item {
            FlowRow(
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
            ) {
                NavigationBackButton(
                    onClick = {},
                )
            }
        }
    }
}
