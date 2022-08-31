package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToEditTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.TransactionsListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.TransactionsListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet.TransactionsDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet.TransactionsFilterBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet.TransactionsSortBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption

internal enum class TransactionsBottomSheetType : BottomSheetType {
    NONE,
    FILTERS,
    SORT,
    DELETE_CONFIRMATION,
}

internal data class TransactionsScreenViewData(
    val expenseCategories: List<Category>,
    val incomeCategories: List<Category>,
    val investmentCategories: List<Category>,
    val selectedExpenseCategoryIndices: List<Int>,
    val selectedIncomeCategoryIndices: List<Int>,
    val selectedInvestmentCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypesIndices: List<Int>,
    val sources: List<Source>,
    val transactionsListItemViewData: Map<String, List<TransactionsListItemViewData>>,
    val navigationManager: NavigationManager,
    val searchText: String,
    val selectedSortOption: SortOption,
    val deleteTransaction: (transactionId: Int) -> Unit,
    val updateSelectedExpenseCategoryIndices: (updatedSelectedExpenseCategoryIndices: List<Int>) -> Unit,
    val updateSelectedIncomeCategoryIndices: (updatedSelectedIncomeCategoryIndices: List<Int>) -> Unit,
    val updateSelectedInvestmentCategoryIndices: (updatedSelectedInvestmentCategoryIndices: List<Int>) -> Unit,
    val updateSelectedSourceIndices: (updatedSelectedSourceIndices: List<Int>) -> Unit,
    val updateSelectedTransactionTypesIndices: (updatedSelectedTransactionTypesIndices: List<Int>) -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Unit,
)

@Composable
internal fun TransactionsScreenView(
    data: TransactionsScreenViewData,
    state: CommonScreenViewState,
) {
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

    // Filter
    val transactionTypes: List<TransactionType> = TransactionType.values().toList()

    // Sorting
    val sortOptions = SortOption.values()

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
                    TransactionsFilterBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        expenseCategories = data.expenseCategories,
                        incomeCategories = data.incomeCategories,
                        investmentCategories = data.investmentCategories,
                        sources = data.sources,
                        transactionTypes = transactionTypes,
                        selectedExpenseCategoryIndices = data.selectedExpenseCategoryIndices,
                        selectedIncomeCategoryIndices = data.selectedIncomeCategoryIndices,
                        selectedInvestmentCategoryIndices = data.selectedInvestmentCategoryIndices,
                        selectedSourceIndices = data.selectedSourceIndices,
                        selectedTransactionTypesIndices = data.selectedTransactionTypesIndices,
                        updateSelectedExpenseCategoryIndices = { updatedSelectedExpenseCategoryIndices ->
                            data.updateSelectedExpenseCategoryIndices(
                                updatedSelectedExpenseCategoryIndices
                            )
                        },
                        updateSelectedIncomeCategoryIndices = { updatedSelectedIncomeCategoryIndices ->
                            data.updateSelectedIncomeCategoryIndices(
                                updatedSelectedIncomeCategoryIndices
                            )
                        },
                        updateSelectedInvestmentCategoryIndices = { updatedSelectedInvestmentCategoryIndices ->
                            data.updateSelectedInvestmentCategoryIndices(
                                updatedSelectedInvestmentCategoryIndices
                            )
                        },
                        updateSelectedSourceIndices = { updatedSelectedSourceIndices ->
                            data.updateSelectedSourceIndices(
                                updatedSelectedSourceIndices
                            )
                        },
                        updateSelectedTransactionTypesIndices = { updatedSelectedTransactionTypesIndices ->
                            data.updateSelectedTransactionTypesIndices(
                                updatedSelectedTransactionTypesIndices
                            )
                        },
                        resetBottomSheetType = {
                            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                        },
                    )
                }
                TransactionsBottomSheetType.SORT -> {
                    TransactionsSortBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        sortOptions = sortOptions.toList(),
                        selectedSortOptionIndex = sortOptions.indexOf(data.selectedSortOption),
                        updateSelectedSortOption = { index ->
                            data.updateSelectedSortOption(sortOptions[index])
                        },
                        resetBottomSheetType = {
                            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                        },
                    )
                }
                TransactionsBottomSheetType.DELETE_CONFIRMATION -> {
                    TransactionsDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        transactionIdToDelete = transactionIdToDelete,
                        resetBottomSheetType = {
                            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                        },
                        resetTransactionIdToDelete = {
                            transactionIdToDelete = null
                        },
                        resetExpandedItemKey = {
                            expandedItemKey = ""
                        },
                        deleteTransaction = {
                            transactionIdToDelete?.let { transactionIdToDeleteValue ->
                                data.deleteTransaction(transactionIdToDeleteValue)
                            }
                        },
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_transactions_appbar_title,
                    navigationAction = {
                        navigateUp(
                            navigationManager = data.navigationManager,
                        )
                    },
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
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            MyScaffoldContentWrapper(
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
                        visible = data.transactionsListItemViewData.isEmpty() && data.searchText.isEmpty(),
                    ) {
                        MyLinearProgressIndicator()
                    }
                    AnimatedVisibility(
                        visible = data.transactionsListItemViewData.isNotEmpty() ||
                                data.searchText.isNotEmpty() ||
                                data.selectedExpenseCategoryIndices.isNotEmpty() ||
                                data.selectedIncomeCategoryIndices.isNotEmpty() ||
                                data.selectedSourceIndices.isNotEmpty() ||
                                data.selectedTransactionTypesIndices.isNotEmpty()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(
                                    minHeight = 48.dp,
                                )
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 6.dp,
                                    bottom = 2.dp,
                                ),
                        ) {
                            AnimatedVisibility(
                                visible = true,
                                modifier = Modifier
                                    .weight(
                                        weight = 1F,
                                    ),
                            ) {
                                MySearchBar(
                                    autoFocus = false,
                                    searchText = data.searchText,
                                    placeholderText = stringResource(
                                        id = R.string.screen_transactions_searchbar_placeholder,
                                    ),
                                    onValueChange = data.updateSearchText,
                                    onSearch = {
                                        state.focusManager.clearFocus()
                                    },
                                )
                            }
                            ElevatedCard(
                                onClick = {
                                    transactionsBottomSheetType =
                                        TransactionsBottomSheetType.SORT
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    )
                                },
                                modifier = Modifier,
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.SwapVert,
                                    contentDescription = stringResource(
                                        id = R.string.screen_transactions_sort_button_content_description,
                                    ),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                        .padding(
                                            all = 8.dp,
                                        ),
                                )
                            }
                            ElevatedCard(
                                onClick = {
                                    transactionsBottomSheetType =
                                        TransactionsBottomSheetType.FILTERS
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    )
                                },
                                modifier = Modifier,
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.FilterAlt,
                                    contentDescription = stringResource(
                                        id = R.string.screen_transactions_filter_button_content_description,
                                    ),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                        .padding(
                                            all = 8.dp,
                                        ),
                                )
                            }
                        }
                    }
                    LazyColumn {
                        data.transactionsListItemViewData.forEach { (date, listItemData) ->
                            if (date.isNotBlank()) {
                                stickyHeader {
                                    MyText(
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.background,
                                            )
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                top = 8.dp,
                                                bottom = 4.dp,
                                                end = 16.dp,
                                            ),
                                        text = date,
                                        style = MaterialTheme.typography.headlineSmall
                                            .copy(
                                                color = MaterialTheme.colorScheme.onBackground,
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
                                            navigationManager = data.navigationManager,
                                            transactionId = listItem.transaction.id,
                                        )
                                        expandedItemKey = ""
                                    },
                                    onDeleteClick = {
                                        transactionIdToDelete = listItem.transaction.id
                                        transactionsBottomSheetType =
                                            TransactionsBottomSheetType.DELETE_CONFIRMATION
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        )
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
