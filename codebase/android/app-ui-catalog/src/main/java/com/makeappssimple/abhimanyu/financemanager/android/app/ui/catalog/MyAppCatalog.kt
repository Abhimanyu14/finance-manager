@file:OptIn(ExperimentalLayoutApi::class)

package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyDefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyNavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBarUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.button.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTab
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData

@Composable
fun MyAppCatalog() {
    MyAppTheme {
        CatalogContent()
    }
}

@Composable
private fun CatalogContent() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val titleStyle = MaterialTheme.typography.titleLarge

    val tabData = listOf(
        MyTabData(
            title = stringResource(
                id = R.string.tab_components,
            ),
        ),
        MyTabData(
            title = stringResource(
                id = R.string.tab_text,
            ),
        ),
        MyTabData(
            title = stringResource(
                id = R.string.tab_color,
            ),
        ),
    )
    val (selectedTabIndex, setSelectedTabIndex) = remember {
        mutableIntStateOf(0)
    }

    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.top_appbar_title,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
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
    context: Context,
    titleStyle: TextStyle,
) {
    overviewTabDemo(
        titleStyle = titleStyle,
    )
    overviewCardDemo(
        titleStyle = titleStyle,
    )
    totalBalanceCardDemo(
        titleStyle = titleStyle,
    )
    selectionGroupDemo(
        titleStyle = titleStyle,
    )
    radioGroupDemo(
        titleStyle = titleStyle,
    )
    horizontalScrollingRadioGroupDemo(
        titleStyle = titleStyle,
    )
    horizontalScrollingSuggestionGroupDemo(
        titleStyle = titleStyle,
        context = context,
    )
    myOutlinedTextFieldDemo(
        titleStyle = titleStyle,
    )
    emojiCircleDemo(
        titleStyle = titleStyle,
    )
    saveButtonDemo(
        titleStyle = titleStyle,
    )
    defaultTagDemo(
        titleStyle = titleStyle,
    )
    mySearchBarDemo(
        titleStyle = titleStyle,
    )
    myTopAppBarDemo(
        titleStyle = titleStyle,
    )
    navigationBackButtonDemo(
        titleStyle = titleStyle,
    )
}

@OptIn(ExperimentalLayoutApi::class)
private fun LazyListScope.navigationBackButtonDemo(
    titleStyle: TextStyle,
) {
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
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyNavigationBackButton(
                onClick = {},
            )
        }
    }
}

private fun LazyListScope.myTopAppBarDemo(
    titleStyle: TextStyle,
) {
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
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyTopAppBarUI(
                isNavigationIconVisible = true,
                titleText = "Title",
                appBarActions = {},
                navigationBackButton = {
                    MyNavigationBackButton(
                        onClick = {},
                    )
                },
            )
            MyTopAppBarUI(
                isNavigationIconVisible = false,
                titleText = "Title",
                appBarActions = {},
                navigationBackButton = {
                    MyNavigationBackButton(
                        onClick = {},
                    )
                },
            )
        }
    }
}

private fun LazyListScope.mySearchBarDemo(
    titleStyle: TextStyle,
) {
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
                    events = MySearchBarEvents(
                        onValueChange = setSearchText,
                    ),
                )
            }
        }
    }
}

private fun LazyListScope.defaultTagDemo(
    titleStyle: TextStyle,
) {
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
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyDefaultTag()
        }
    }
}

private fun LazyListScope.overviewCardDemo(
    titleStyle: TextStyle,
) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            )
        ) {
            OverviewCard(
                data = OverviewCardData(
                    overviewTabSelectionIndex = 1,
                    pieChartData = PieChartData(
                        items = listOf(
                            PieChartItemData(
                                value = 500F,
                                text = "Income : ₹500",
                                color = MyColor.TERTIARY,
                            ),
                            PieChartItemData(
                                value = 600F,
                                text = "Expense : ₹600",
                                color = MyColor.ERROR,
                            ),
                        ),
                    ),
                    title = "2023",
                ),
                events = OverviewCardEvents(
                    onClick = {},
                    onOverviewTabClick = {},
                    handleOverviewCardAction = {},
                ),
            )
            OverviewCard(
                data = OverviewCardData(
                    isLoading = true,
                ),
            )
        }
    }
}

private fun LazyListScope.overviewTabDemo(
    titleStyle: TextStyle,
) {
    item {
        MyText(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            text = "Overview Tab",
            style = titleStyle,
        )
    }
    item {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
            modifier = Modifier
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

private fun LazyListScope.totalBalanceCardDemo(
    titleStyle: TextStyle,
) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            )
        ) {
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    totalBalanceAmount = 1234567890,
                ),
            )
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    isLoading = true,
                    totalBalanceAmount = 1234567890,
                ),
            )
        }
    }
}

private fun LazyListScope.horizontalScrollingSuggestionGroupDemo(
    context: Context,
    titleStyle: TextStyle,
) {
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
            data = MyHorizontalScrollingSelectionGroupData(
                items = items,
            ),
            events = MyHorizontalScrollingSelectionGroupEvents(
                onSelectionChange = {
                    Toast.makeText(
                        context,
                        "Selected ${items[it].text}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            ),
        )
    }
}

private fun LazyListScope.horizontalScrollingRadioGroupDemo(
    titleStyle: TextStyle,
) {
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
            mutableIntStateOf(0)
        }
        MyHorizontalScrollingRadioGroup(
            data = MyHorizontalScrollingRadioGroupData(
                items = items,
                selectedItemIndex = selectedItemIndex,
            ),
            events = MyHorizontalScrollingRadioGroupEvents(
                onSelectionChange = {
                    selectedItemIndex = it
                },
            ),
        )
    }
}

private fun LazyListScope.radioGroupDemo(
    titleStyle: TextStyle,
) {
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
            mutableIntStateOf(0)
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
}

private fun LazyListScope.selectionGroupDemo(
    titleStyle: TextStyle,
) {
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
            data = MySelectionGroupData(
                items = items,
                selectedItemsIndices = selectedItemsIndices,
            ),
            events = MySelectionGroupEvents(
                onSelectionChange = {
                    selectedItemsIndices.addIfDoesNotContainItemElseRemove(it)
                },
            ),
        )
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
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    isLoading = true,
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    isLoading = true,
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    isLoading = true,
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
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
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                data = MyOutlinedTextFieldData(
                    labelTextStringResourceId = R.string.my_outlined_text_field_label,
                    trailingIconContentDescriptionTextStringResourceId = R.string.common_empty_string,
                ),
            )
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                data = MyOutlinedTextFieldData(
                    isLoading = true,
                    labelTextStringResourceId = R.string.my_outlined_text_field_label,
                    trailingIconContentDescriptionTextStringResourceId = R.string.common_empty_string,
                ),
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
