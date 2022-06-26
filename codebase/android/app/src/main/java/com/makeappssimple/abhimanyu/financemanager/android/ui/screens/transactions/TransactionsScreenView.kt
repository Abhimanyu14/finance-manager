package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyScrollableRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.ConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.ConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.SearchBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.SearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue50
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateString
import kotlin.math.abs

enum class TransactionsBottomSheetType : BottomSheetType {
    NONE,
    FILTERS,
    DELETE_CONFIRMATION,
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

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun TransactionsScreenView(
    data: TransactionsScreenViewData,
    state: TransactionsScreenViewState,
) {
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
    var transactionIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
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
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        transactionsBottomSheetType = TransactionsBottomSheetType.NONE
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
                TransactionsBottomSheetType.DELETE_CONFIRMATION -> {
                    ConfirmationBottomSheet(
                        data = ConfirmationBottomSheetData(
                            title = stringResource(
                                id = R.string.screen_transactions_bottom_sheet_delete_title,
                            ),
                            message = stringResource(
                                id = R.string.screen_transactions_bottom_sheet_delete_message,
                            ),
                            positiveButtonText = stringResource(
                                id = R.string.screen_transactions_bottom_sheet_delete_positive_button_text,
                            ),
                            negativeButtonText = stringResource(
                                id = R.string.screen_transactions_bottom_sheet_delete_negative_button_text,
                            ),
                            onPositiveButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    transactionIdToDelete?.let { transactionIdToDeleteValue ->
                                        data.screenViewModel.deleteTransaction(
                                            id = transactionIdToDeleteValue,
                                        )
                                        transactionIdToDelete = null
                                        expandedItemKey = ""
                                    }
                                    transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                                }
                            },
                            onNegativeButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                                    transactionIdToDelete = null
                                }
                            },
                        ),
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleTextStringResourceId = R.string.screen_transactions_appbar_title,
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
                        visible = groupedTransactions.isNotEmpty() || searchText.isNotEmpty(),
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
                                        onValueChange = updateSearchText,
                                        onSearch = {
                                            state.focusManager.clearFocus()
                                        },
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
                                        if (isSearchbarVisible) {
                                            state.keyboardController?.hide()
                                        }
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
                                        tint = DarkGray,
                                        modifier = Modifier
                                            .background(
                                                color = Blue50,
                                            )
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
                                        tint = DarkGray,
                                        modifier = Modifier
                                            .background(
                                                color = Blue50,
                                            )
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
                                        tint = DarkGray,
                                        modifier = Modifier
                                            .background(
                                                color = Blue50,
                                            )
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
                                            color = DarkGray,
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
                                        transactionIdToDelete = listItem.transaction.id
                                        transactionsBottomSheetType = TransactionsBottomSheetType
                                            .DELETE_CONFIRMATION
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        ) {}
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
