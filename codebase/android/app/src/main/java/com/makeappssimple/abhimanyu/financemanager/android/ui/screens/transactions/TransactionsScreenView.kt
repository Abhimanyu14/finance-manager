package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyScrollableRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.SearchBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.SearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateString
import kotlin.math.abs

enum class TransactionsBottomSheetType {
    NONE,
    FILTERS,
}

enum class SortOption(
    val title: String,
) {
    AMOUNT_ASC(
        title = "Amount Asc",
    ),
    AMOUNT_DESC(
        title = "Amount Desc",
    ),
    LATEST_FIRST(
        title = "Latest First",
    ),
    OLDEST_FIRST(
        title = "Oldest First",
    ),
}

data class TransactionsScreenViewData(
    val screenViewModel: TransactionsScreenViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun TransactionsScreenView(
    data: TransactionsScreenViewData,
    state: TransactionsScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val categories: List<Category> by data.screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    val expenseCategories = categories.filter {
        it.transactionType == TransactionType.EXPENSE
    }
    val incomeCategories = categories.filter {
        it.transactionType == TransactionType.INCOME
    }
    val sources: List<Source> by data.screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val transactionsListItemViewData by data.screenViewModel.transactionsListItemViewData.collectAsState(
        initial = emptyList(),
    )
    var transactionsBottomSheetType by remember {
        mutableStateOf(
            value = TransactionsBottomSheetType.NONE,
        )
    }
    var expandedItemKey by remember {
        mutableStateOf(
            value = "",
        )
    }

    // Search
    val (isSearchbarVisible, setIsSearchbarVisible) = remember {
        mutableStateOf(
            value = false,
        )
    }
    val (searchText, updateSearchText) = remember {
        mutableStateOf(
            value = "",
        )
    }

    // Sorting
    val (isSortOptionsVisible, setIsSortOptionsVisible) = remember {
        mutableStateOf(
            value = false,
        )
    }
    val sortOptions = SortOption.values()
    val (selectedSortOption, updateSelectedSortOption) = remember {
        mutableStateOf(
            value = SortOption.LATEST_FIRST,
        )
    }

    // Filter
    val selectedExpenseCategoryIndices = remember {
        mutableStateListOf<Int>()
    }
    val selectedIncomeCategoryIndices = remember {
        mutableStateListOf<Int>()
    }
    val selectedSourceIndices = remember {
        mutableStateListOf<Int>()
    }
    val transactionTypes: List<TransactionType> = TransactionType.values().toList()
    val selectedTransactionTypesIndices = remember {
        mutableStateListOf<Int>()
    }

    // Transactions search, filter and sort
    val searchedTransactions = if (searchText.isNotBlank()) {
        transactionsListItemViewData
            .filter {
                it.transaction.title.contains(
                    other = searchText,
                    ignoreCase = true,
                )
            }
    } else {
        transactionsListItemViewData
    }
    val transactionTypeFilteredTransactions =
        if (selectedTransactionTypesIndices.isNotEmpty()) {
            searchedTransactions.filter {
                selectedTransactionTypesIndices.contains(
                    transactionTypes.indexOf(it.transaction.transactionType)
                )
            }
        } else {
            searchedTransactions
        }
    val sourceFilteredTransactions = if (selectedSourceIndices.isNotEmpty()) {
        transactionTypeFilteredTransactions.filter {
            selectedSourceIndices.contains(sources.indexOf(it.sourceFrom)) ||
                    selectedSourceIndices.contains(sources.indexOf(it.sourceTo))
        }
    } else {
        transactionTypeFilteredTransactions
    }
    val categoryFilteredTransactions =
        if (selectedExpenseCategoryIndices.isNotEmpty() || selectedIncomeCategoryIndices.isNotEmpty()) {
            sourceFilteredTransactions.filter {
                selectedExpenseCategoryIndices.contains(expenseCategories.indexOf(it.category)) ||
                        selectedIncomeCategoryIndices.contains(incomeCategories.indexOf(it.category))
            }
        } else {
            sourceFilteredTransactions
        }
    val filteredTransactions = categoryFilteredTransactions
    val sortedTransactions = when (selectedSortOption) {
        SortOption.AMOUNT_ASC -> {
            filteredTransactions.sortedBy {
                abs(it.transaction.amount.value)
            }
        }
        SortOption.AMOUNT_DESC -> {
            filteredTransactions.sortedByDescending {
                abs(it.transaction.amount.value)
            }
        }
        SortOption.LATEST_FIRST -> {
            filteredTransactions.sortedByDescending {
                it.transaction.transactionTimestamp
            }
        }
        SortOption.OLDEST_FIRST -> {
            filteredTransactions.sortedBy {
                it.transaction.transactionTimestamp
            }
        }
    }
    val groupedTransactions: Map<String, List<TransactionsListItemViewData>> =
        if (selectedSortOption == SortOption.LATEST_FIRST || selectedSortOption == SortOption.OLDEST_FIRST) {
            sortedTransactions
                .groupBy {
                    getDateString(
                        it.transaction.transactionTimestamp
                    )
                }
        } else {
            sortedTransactions.groupBy {
                ""
            }
        }


    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
        }
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = if (state.modalBottomSheetState.currentValue == ModalBottomSheetValue.Expanded) {
            BottomSheetExpandedShape
        } else {
            BottomSheetShape
        },
        sheetContent = {
            when (transactionsBottomSheetType) {
                TransactionsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                TransactionsBottomSheetType.FILTERS -> {
                    TransactionsFiltersBottomSheet(
                        data = TransactionsFilterBottomSheetData(
                            expenseCategories = expenseCategories,
                            incomeCategories = incomeCategories,
                            sources = sources,
                            transactionTypes = transactionTypes,
                            selectedExpenseCategoryIndices = selectedExpenseCategoryIndices,
                            selectedIncomeCategoryIndices = selectedIncomeCategoryIndices,
                            selectedSourceIndices = selectedSourceIndices,
                            selectedTransactionTypesIndices = selectedTransactionTypesIndices,
                            onPositiveButtonClick = {
                                selectedExpenseCategoryIndices.clear()
                                selectedIncomeCategoryIndices.clear()
                                selectedSourceIndices.clear()
                                selectedTransactionTypesIndices.clear()

                                selectedExpenseCategoryIndices.addAll(it.selectedExpenseCategoryIndices)
                                selectedIncomeCategoryIndices.addAll(it.selectedIncomeCategoryIndices)
                                selectedSourceIndices.addAll(it.selectedSourceIndices)
                                selectedTransactionTypesIndices.addAll(it.selectedTransactionTypeIndices)

                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                                }
                            },
                            onNegativeButtonClick = {},
                        ),
                    )
                }
            }
        },
    ) {
        Scaffold(
            scaffoldState = state.scaffoldState,
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleText = stringResource(
                        id = R.string.screen_transactions_appbar_title,
                    ),
                    isNavigationIconVisible = true,
                )
            },
            floatingActionButton = {
                MyFloatingActionButton(
                    iconImageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_transactions_floating_action_button_content_description,
                    ),
                    onClick = {
                        navigateToAddTransactionScreen(
                            navigationManager = data.screenViewModel.navigationManager,
                        )
                    },
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            ScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    AnimatedVisibility(
                        visible = groupedTransactions.isNotEmpty(),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(
                                    minHeight = 84.dp,
                                )
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 2.dp,
                                ),
                        ) {
                            AnimatedVisibility(
                                visible = isSearchbarVisible,
                                modifier = Modifier
                                    .weight(
                                        weight = 1F,
                                    ),
                            ) {
                                SearchBar(
                                    data = SearchBarData(
                                        searchText = searchText,
                                        placeholderText = stringResource(
                                            id = R.string.screen_transactions_searchbar_placeholder,
                                        ),
                                        updateSearchText = updateSearchText,
                                    ),
                                )
                            }
                            AnimatedVisibility(
                                visible = isSortOptionsVisible,
                                modifier = Modifier
                                    .weight(
                                        weight = 1F,
                                    ),
                            ) {
                                MyScrollableRadioGroup(
                                    items = sortOptions
                                        .map { sortOption ->
                                            MyRadioGroupItem(
                                                text = sortOption.title,
                                            )
                                        },
                                    selectedItemIndex = sortOptions.indexOf(selectedSortOption),
                                    onSelectionChange = { index ->
                                        updateSelectedSortOption(sortOptions[index])
                                        setIsSortOptionsVisible(!isSortOptionsVisible)
                                    },
                                    modifier = Modifier
                                        .padding(
                                            all = 12.dp,
                                        ),
                                )
                            }
                            AnimatedVisibility(
                                visible = !isSortOptionsVisible,
                            ) {
                                ElevatedCard(
                                    onClick = {
                                        setIsSearchbarVisible(!isSearchbarVisible)
                                        updateSearchText("")
                                    },
                                    modifier = Modifier,
                                ) {
                                    Icon(
                                        imageVector = if (isSearchbarVisible) {
                                            Icons.Rounded.Clear
                                        } else {
                                            Icons.Rounded.Search
                                        },
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_category_clear_title,
                                        ),
                                        modifier = Modifier
                                            .padding(
                                                all = 8.dp,
                                            ),
                                    )
                                }
                            }
                            AnimatedVisibility(
                                visible = !isSearchbarVisible,
                            ) {
                                ElevatedCard(
                                    onClick = {
                                        setIsSortOptionsVisible(!isSortOptionsVisible)
                                    },
                                    modifier = Modifier,
                                ) {
                                    Icon(
                                        imageVector = if (isSortOptionsVisible) {
                                            Icons.Rounded.Clear
                                        } else {
                                            Icons.Rounded.SwapVert
                                        },
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_category_clear_title,
                                        ),
                                        modifier = Modifier
                                            .padding(
                                                all = 8.dp,
                                            ),
                                    )
                                }
                            }
                            AnimatedVisibility(
                                visible = !isSearchbarVisible && !isSortOptionsVisible,
                            ) {
                                ElevatedCard(
                                    onClick = {
                                        transactionsBottomSheetType =
                                            TransactionsBottomSheetType.FILTERS
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        ) {}
                                    },
                                    modifier = Modifier,
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.FilterAlt,
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_category_clear_title,
                                        ),
                                        modifier = Modifier
                                            .padding(
                                                all = 8.dp,
                                            ),
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn {
                        groupedTransactions.forEach { (date, listItemData) ->
                            if (date.isNotBlank()) {
                                stickyHeader {
                                    MyText(
                                        text = date,
                                        style = TextStyle(
                                            color = Color.DarkGray,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        modifier = Modifier
                                            .background(
                                                color = Surface,
                                            )
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                top = 8.dp,
                                                bottom = 4.dp,
                                                end = 16.dp,
                                            ),
                                    )
                                }
                            }
                            itemsIndexed(
                                items = listItemData,
                                key = { _, listItem ->
                                    listItem.hashCode()
                                },
                            ) { index, listItem ->
                                TransactionsListItem(
                                    data = listItem,
                                    expanded = "$date $index" == expandedItemKey,
                                    deleteEnabled = true,
                                    onClick = {
                                        expandedItemKey = if ("$date $index" == expandedItemKey) {
                                            ""
                                        } else {
                                            "$date $index"
                                        }
                                    },
                                    onEditClick = {
                                        navigateToEditTransactionScreen(
                                            navigationManager = data.screenViewModel.navigationManager,
                                            transactionId = listItem.transaction.id,
                                        )
                                        expandedItemKey = ""
                                    },
                                    onDeleteClick = {
                                        data.screenViewModel.deleteTransaction(
                                            id = listItem.transaction.id,
                                        )
                                        expandedItemKey = ""
                                    },
                                )
                            }
                        }
                        item {
                            VerticalSpacer(
                                height = 72.dp,
                            )
                        }
                    }
                }
            }
        }
    }
}
