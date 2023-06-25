package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyDefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyNavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBarView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.SearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardView

@Composable
fun MyAppCatalog() {
    MyAppTheme {
        Surface {
            CatalogContent()
        }
    }
}

@Composable
private fun CatalogContent() {
    val context = LocalContext.current
    val titleStyle = MaterialTheme.typography.titleLarge

    val tabData = listOf(
        MyTabData(
            title = "Components",
        ),
        MyTabData(
            title = "Text",
        ),
        MyTabData(
            title = "Color",
        ),
    )
    val (selectedTabIndex, setSelectedTabIndex) = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        MyText(
            text = "UI Catalog",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(
                    all = 16.dp,
                ),
        )
        MyTabRow(
            selectedTabIndex = selectedTabIndex,
            updateSelectedTabIndex = setSelectedTabIndex,
            tabData = tabData,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                all = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
            ),
        ) {
            if (selectedTabIndex == 0) {
                componentsTabContent(
                    titleStyle = titleStyle,
                    context = context,
                )
            }
            if (selectedTabIndex == 1) {
                textTabContent()
            }
            if (selectedTabIndex == 2) {
                colorTabContent()
            }
        }
    }
}

private fun LazyListScope.componentsTabContent(
    titleStyle: TextStyle,
    context: Context,
) {
    myOutlinedTextFieldDemo(
        titleStyle = titleStyle,
    )
    emojiCircleDemo(
        titleStyle = titleStyle,
    )
    saveButtonDemo(
        titleStyle = titleStyle,
    )

    /**
     * Selection Group
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Selection Group",
            style = titleStyle,
        )
    }
    item {
        val items = listOf(
            ChipUIData(
                text = "Item 1",
            ),
            ChipUIData(
                text = "Item 2",
            ),
            ChipUIData(
                text = "Item 3",
            ),
            ChipUIData(
                text = "Item 4",
            ),
            ChipUIData(
                text = "Item 5",
            ),
            ChipUIData(
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
                selectedItemsIndices.addIfDoesNotContainItemElseRemove(it)
            },
        )
    }

    /**
     * Radio Group
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Radio Group",
            style = titleStyle,
        )
    }
    item {
        val items = listOf(
            ChipUIData(
                text = "Item 1",
            ),
            ChipUIData(
                text = "Item 2",
            ),
            ChipUIData(
                text = "Item 3",
            ),
            ChipUIData(
                text = "Item 4",
            ),
            ChipUIData(
                text = "Item 5",
            ),
            ChipUIData(
                text = "Item 6",
            ),
        )
        var selectedItemIndex by remember {
            mutableStateOf(0)
        }
        MyRadioGroup(
            data = MyRadioGroupData(
                items = items,
                selectedItemIndex = selectedItemIndex,
            ),
            events = MyRadioGroupEvents(
                onSelectionChange = {
                    selectedItemIndex = it
                },
            ),
        )
    }

    /**
     * Horizontal Scrolling Radio Group
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Horizontal Scrolling Radio Group",
            style = titleStyle,
        )
    }
    item {
        val items = listOf(
            ChipUIData(
                text = "Item 1",
            ),
            ChipUIData(
                text = "Item 2",
            ),
            ChipUIData(
                text = "Item 3",
            ),
            ChipUIData(
                text = "Item 4",
            ),
            ChipUIData(
                text = "Item 5",
            ),
            ChipUIData(
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

    /**
     * Horizontal Scrolling Suggestion Group
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Horizontal Scrolling Suggestion Group",
            style = titleStyle,
        )
    }
    item {
        val items = listOf(
            ChipUIData(
                text = "Item 1",
            ),
            ChipUIData(
                text = "Item 2",
            ),
            ChipUIData(
                text = "Item 3",
            ),
            ChipUIData(
                text = "Item 4",
            ),
            ChipUIData(
                text = "Item 5",
            ),
            ChipUIData(
                text = "Item 6",
            ),
        )
        MyHorizontalScrollingSelectionGroup(
            items = items,
            onSelectionChange = {
                Toast.makeText(
                    context,
                    "Selected ${items[it].text}",
                    Toast.LENGTH_SHORT,
                ).show()
            },
        )
    }

    /**
     * Total Balance Card
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Total Balance Card",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            TotalBalanceCardView(
                totalBalanceAmount = 1234567890,
            )
        }
    }

    /**
     * Overview Card
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Overview Card",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            OverviewCardView(
                data = OverviewCardViewData(
                    overviewTabSelectionIndex = 1,
                    title = "2023",
                    pieChartData = PieChartData(
                        items = listOf(
                            PieChartItemData(
                                value = 500F,
                                text = "Income : ₹500",
                                color = MaterialTheme.colorScheme.tertiary,
                            ),
                            PieChartItemData(
                                value = 600F,
                                text = "Expense : ₹600",
                                color = MaterialTheme.colorScheme.error,
                            ),
                        ),
                    ),
                    onClick = {},
                    onOverviewTabClick = {},
                    handleOverviewCardAction = {},
                ),
            )
        }
    }

    /**
     * Default Tag
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Default Tag",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            MyDefaultTag()
        }
    }

    /**
     * Search Bar
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Search Bar",
            style = titleStyle,
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
            MySearchBarContainer {
                SearchBar(
                    autoFocus = false,
                    searchText = searchText,
                    placeholderText = "Placeholder",
                    onValueChange = setSearchText,
                )
            }
        }
    }

    /**
     * My Search Bar
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "My Search Bar",
            style = titleStyle,
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
            MySearchBarContainer {
                MySearchBar(
                    autoFocus = false,
                    searchText = searchText,
                    placeholderText = "Placeholder",
                    onValueChange = setSearchText,
                )
            }
        }
    }

    /**
     * My Top App Bar
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "My Top App Bar",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            MyTopAppBarView(
                isNavigationIconVisible = true,
                titleText = "Title",
                navigationBackButton = {
                    MyNavigationBackButton(
                        onClick = {},
                    )
                },
            )
            MyTopAppBarView(
                isNavigationIconVisible = false,
                titleText = "Title",
                navigationBackButton = {
                    MyNavigationBackButton(
                        onClick = {},
                    )
                },
            )
        }
    }

    /**
     * Navigation Back Button
     */
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Navigation Back Button",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            MyNavigationBackButton(
                onClick = {},
            )
        }
    }
}

private fun LazyListScope.emojiCircleDemo(
    titleStyle: TextStyle,
) {
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Emoji Circle",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    isLoading = true,
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    isLoading = true,
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    isLoading = true,
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    emoji = "😀",
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    emoji = "😀",
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    emoji = "😀",
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
                events = MyEmojiCircleEvents(),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
                events = MyEmojiCircleEvents(),
            )
        }
    }
}

private fun LazyListScope.saveButtonDemo(
    titleStyle: TextStyle,
) {
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Save Button",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = false,
                    textStringResourceId = R.string.save_button,
                ),
                events = SaveButtonEvents(
                    onClick = {},
                ),
            )
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = true,
                    textStringResourceId = R.string.save_button,
                ),
                events = SaveButtonEvents(
                    onClick = {},
                ),
            )
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = true,
                    isLoading = true,
                    textStringResourceId = R.string.save_button,
                ),
                events = SaveButtonEvents(
                    onClick = {},
                ),
            )
        }
    }
}

private fun LazyListScope.myOutlinedTextFieldDemo(
    titleStyle: TextStyle,
) {
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "My Outlined TextField",
            style = titleStyle,
        )
    }
    item {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
        ) {
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                data = MyOutlinedTextFieldData(
                    labelTextStringResourceId = R.string.my_outlined_text_field_label,
                    trailingIconContentDescriptionTextStringResourceId = R.string.common_empty_string,
                ),
                events = MyOutlinedTextFieldEvents(),
            )
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                data = MyOutlinedTextFieldData(
                    isLoading = true,
                    labelTextStringResourceId = R.string.my_outlined_text_field_label,
                    trailingIconContentDescriptionTextStringResourceId = R.string.common_empty_string,
                ),
                events = MyOutlinedTextFieldEvents(),
            )
        }
    }
}

private fun LazyListScope.textTabContent() {
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
}

private fun LazyListScope.colorTabContent() {
    item {
        Column {
            /**
             * Primary
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                text = "Primary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onPrimary,
                textColor = MaterialTheme.colorScheme.primary,
                text = "On Primary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = "Primary Container",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onPrimaryContainer,
                textColor = MaterialTheme.colorScheme.primaryContainer,
                text = "On Primary Container",
            )

            /**
             * Secondary
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary,
                text = "Secondary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSecondary,
                textColor = MaterialTheme.colorScheme.secondary,
                text = "On Secondary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                text = "Secondary Container",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSecondaryContainer,
                textColor = MaterialTheme.colorScheme.secondaryContainer,
                text = "On Secondary Container",
            )

            /**
             * Tertiary
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.tertiary,
                textColor = MaterialTheme.colorScheme.onTertiary,
                text = "Tertiary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onTertiary,
                textColor = MaterialTheme.colorScheme.tertiary,
                text = "On Tertiary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.tertiaryContainer,
                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                text = "Tertiary Container",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onTertiaryContainer,
                textColor = MaterialTheme.colorScheme.tertiaryContainer,
                text = "On Tertiary Container",
            )

            /**
             * Error
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.error,
                textColor = MaterialTheme.colorScheme.onError,
                text = "Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onError,
                textColor = MaterialTheme.colorScheme.error,
                text = "On Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.errorContainer,
                textColor = MaterialTheme.colorScheme.onErrorContainer,
                text = "Surface Variant",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onErrorContainer,
                textColor = MaterialTheme.colorScheme.errorContainer,
                text = "On Surface Variant",
            )

            /**
             * Background
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground,
                text = "Background",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onBackground,
                textColor = MaterialTheme.colorScheme.background,
                text = "On Background",
            )

            /**
             * Surface
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.surface,
                textColor = MaterialTheme.colorScheme.onSurface,
                text = "Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSurface,
                textColor = MaterialTheme.colorScheme.surface,
                text = "On Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.surfaceVariant,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "Surface Variant",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSurfaceVariant,
                textColor = MaterialTheme.colorScheme.surfaceVariant,
                text = "On Surface Variant",
            )

            /**
             * Outline
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.outline,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "Outline",
            )
        }
    }
}

@Composable
private fun ColorSwatch(
    surfaceColor: Color,
    textColor: Color,
    text: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = 160.dp,
            )
            .padding(
                all = 16.dp,
            ),
        color = surfaceColor,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    all = 24.dp,
                ),
            text = text,
            style = MaterialTheme.typography.displaySmall
                .copy(
                    color = textColor,
                ),
        )
    }
}
