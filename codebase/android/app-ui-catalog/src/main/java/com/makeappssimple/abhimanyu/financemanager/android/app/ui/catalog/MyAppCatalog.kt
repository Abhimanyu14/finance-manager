package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Green700
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Red
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.DefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBarView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.NavigationBackButtonView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCardView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCardViewData
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
                                    color = Red,
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
                    backgroundColor = Color.LightGray,
                )
                EmojiCircle(
                    emoji = "😀",
                    backgroundColor = Color.LightGray,
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
                        NavigationBackButtonView(
                            onClick = {},
                        )
                    },
                )
                MyTopAppBarView(
                    titleText = "Title",
                    isNavigationIconVisible = false,
                    navigationBackButton = {
                        NavigationBackButtonView(
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
                NavigationBackButtonView(
                    onClick = {},
                )
            }
        }
    }
}
