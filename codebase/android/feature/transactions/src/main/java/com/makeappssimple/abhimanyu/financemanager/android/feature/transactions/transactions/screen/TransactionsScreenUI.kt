package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MySelectionModeTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_transaction_for.SelectTransactionForBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_transaction_for.SelectTransactionForBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_transaction_for.SelectTransactionForBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet.TransactionsFilterBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet.TransactionsSortBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import java.time.LocalDate

enum class TransactionsBottomSheetType : BottomSheetType {
    FILTERS,
    NONE,
    SELECT_TRANSACTION_FOR,
    SORT,
}

@Immutable
data class TransactionsScreenUIData(
    val isLoading: Boolean = false,
    val selectedFilter: Filter = Filter(),
    val selectedTransactions: List<Int> = emptyList(),
    val sortOptions: List<SortOption> = emptyList(),
    val transactionTypes: List<TransactionType> = emptyList(),
    val transactionForValues: List<TransactionFor> = emptyList(),
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val currentTimeMillis: Long = 0L,
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> = emptyMap(),
    val searchText: String = "",
    val selectedSortOption: SortOption = SortOption.LATEST_FIRST,
)

@Immutable
internal data class TransactionsScreenUIEvents(
    val addToSelectedTransactions: (Int) -> Unit,
    val clearSelectedTransactions: () -> Unit,
    val getExpenseCategories: () -> List<Category>,
    val getIncomeCategories: () -> List<Category>,
    val getInvestmentCategories: () -> List<Category>,
    val getSources: () -> List<Source>,
    val getTransactionForValues: () -> List<TransactionFor>,
    val navigateToAddTransactionScreen: () -> Unit,
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val removeFromSelectedTransactions: (Int) -> Unit,
    val toggleTransactionSelection: (Int) -> Unit,
    val updateSearchText: (String) -> Unit,
    val updateSelectedFilter: (Filter) -> Unit,
    val updateSelectedSortOption: (SortOption) -> Unit,
    val updateTransactionForValuesInTransactions: (Int) -> Unit,
)

@Composable
internal fun TransactionsScreenUI(
    events: TransactionsScreenUIEvents,
    uiState: TransactionsScreenUIState,
    state: CommonScreenUIState,
) {
    val resetSelectionMode = {
        uiState.setIsInSelectionMode(false)
        events.clearSelectedTransactions()
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        bottomSheetType = uiState.transactionsBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    BackHandler(
        enabled = uiState.searchText.isNotEmpty() ||
                uiState.selectedFilter.areFiltersSelected() ||
                uiState.isInSelectionMode,
    ) {
        events.updateSearchText("")
        events.updateSelectedFilter(Filter())
        resetSelectionMode()
    }

    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.transactionsBottomSheetType) {
                TransactionsBottomSheetType.FILTERS -> {
                    TransactionsFilterBottomSheet(
                        context = state.context,
                        expenseCategories = events.getExpenseCategories(),
                        incomeCategories = events.getIncomeCategories(),
                        investmentCategories = events.getInvestmentCategories(),
                        sources = events.getSources(),
                        transactionForValues = events.getTransactionForValues(),
                        transactionTypes = uiState.transactionTypes,
                        defaultMinDate = uiState.oldestTransactionLocalDate,
                        defaultMaxDate = uiState.currentLocalDate,
                        currentTimeMillis = uiState.currentTimeMillis,
                        selectedFilter = uiState.selectedFilter,
                        updateSelectedFilter = { updatedSelectedFilter ->
                            events.updateSelectedFilter(updatedSelectedFilter)
                        },
                        resetBottomSheetType = uiState.resetBottomSheetType,
                    )
                }

                TransactionsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                TransactionsBottomSheetType.SELECT_TRANSACTION_FOR -> {
                    SelectTransactionForBottomSheet(
                        data = SelectTransactionForBottomSheetData(
                            transactionForValues = uiState.transactionForValues,
                        ),
                        events = SelectTransactionForBottomSheetEvents(
                            onItemClick = {
                                uiState.setIsInSelectionMode(false)
                                events.updateTransactionForValuesInTransactions(it.id)
                                uiState.resetBottomSheetType()
                            },
                        ),
                    )
                }

                TransactionsBottomSheetType.SORT -> {
                    TransactionsSortBottomSheet(
                        selectedSortOptionIndex = uiState.sortOptions.indexOf(uiState.selectedSortOption),
                        sortOptions = uiState.sortOptions.toList(),
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        updateSelectedSortOption = { index ->
                            events.updateSelectedSortOption(uiState.sortOptions[index])
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        sheetShape = when (uiState.transactionsBottomSheetType) {
            TransactionsBottomSheetType.NONE,
            TransactionsBottomSheetType.SELECT_TRANSACTION_FOR,
            TransactionsBottomSheetType.SORT,
            -> {
                BottomSheetShape
            }

            TransactionsBottomSheetType.FILTERS -> {
                BottomSheetExpandedShape
            }
        },
        topBar = {
            if (uiState.isInSelectionMode) {
                MySelectionModeTopAppBar(
                    appBarActions = {
                        var isDropDownVisible by remember {
                            mutableStateOf(false)
                        }
                        Box(
                            modifier = Modifier,
                        ) {
                            IconButton(
                                onClick = {
                                    isDropDownVisible = true
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                            DropdownMenu(
                                expanded = isDropDownVisible,
                                onDismissRequest = {
                                    isDropDownVisible = false
                                }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(
                                                id = R.string.screen_transactions_selection_mode_appbar_menu_update_transaction_for,
                                            ),
                                        )
                                    },
                                    onClick = {
                                        isDropDownVisible = false
                                        uiState.setTransactionsBottomSheetType(
                                            TransactionsBottomSheetType.SELECT_TRANSACTION_FOR
                                        )
                                    },
                                )
                            }
                        }
                    },
                    navigationAction = resetSelectionMode,
                    title = {
                        Text(
                            text = stringResource(
                                id = R.string.screen_transactions_selection_mode_appbar_title,
                                uiState.selectedTransactions.size,
                            ),
                            style = MaterialTheme.typography.titleLarge
                                .copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                ),
                        )
                    }
                )
            } else {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_transactions_appbar_title,
                    navigationAction = events.navigateUp,
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = uiState.isInSelectionMode.not(),
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                MyFloatingActionButton(
                    modifier = Modifier
                        .navigationBarSpacer(),
                    iconImageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_transactions_floating_action_button_content_description,
                    ),
                    onClick = events.navigateToAddTransactionScreen,
                )
            }
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        backHandlerEnabled = uiState.transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            AnimatedVisibility(
                visible = uiState.isLoading,
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = uiState.isInSelectionMode.not() && (
                        uiState.transactionDetailsListItemViewData.isNotEmpty() ||
                                uiState.searchText.isNotEmpty() ||
                                uiState.selectedFilter.areFiltersSelected()
                        )
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
                            data = MySearchBarData(
                                autoFocus = false,
                                placeholderText = stringResource(
                                    id = R.string.screen_transactions_searchbar_placeholder,
                                ),
                                searchText = uiState.searchText,
                            ),
                            events = MySearchBarEvents(
                                onSearch = {
                                    state.focusManager.clearFocus()
                                },
                                onValueChange = events.updateSearchText,
                            ),
                        )
                    }
                    ActionButton(
                        data = ActionButtonData(
                            imageVector = Icons.Rounded.SwapVert,
                            contentDescriptionStringResourceId = R.string.screen_transactions_sort_button_content_description,
                        ),
                        events = ActionButtonEvents(
                            onClick = {
                                uiState.setTransactionsBottomSheetType(
                                    TransactionsBottomSheetType.SORT
                                )
                            },
                        ),
                    )
                    ActionButton(
                        data = ActionButtonData(
                            isIndicatorVisible = uiState.selectedFilter.areFiltersSelected(),
                            imageVector = Icons.Rounded.FilterAlt,
                            contentDescriptionStringResourceId = R.string.screen_transactions_filter_button_content_description,
                        ),
                        events = ActionButtonEvents(
                            onClick = {
                                uiState.setTransactionsBottomSheetType(
                                    TransactionsBottomSheetType.FILTERS
                                )
                            },
                        ),
                    )
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = 72.dp,
                ),
            ) {
                uiState.transactionDetailsListItemViewData.forEach { (date, listItemData) ->
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
                    ) { _, listItem ->
                        val isSelected =
                            uiState.selectedTransactions.contains(listItem.transactionId)
                        TransactionListItem(
                            data = listItem.copy(
                                isInSelectionMode = uiState.isInSelectionMode,
                                isSelected = isSelected,
                            ),
                            events = TransactionListItemEvents(
                                onClick = {
                                    if (uiState.isInSelectionMode) {
                                        if (isSelected) {
                                            events.removeFromSelectedTransactions(listItem.transactionId)
                                        } else {
                                            events.addToSelectedTransactions(listItem.transactionId)
                                        }
                                    } else {
                                        events.navigateToViewTransactionScreen(listItem.transactionId)
                                    }
                                },
                                onLongClick = {
                                    if (uiState.isInSelectionMode) {
                                        if (isSelected) {
                                            events.removeFromSelectedTransactions(listItem.transactionId)
                                        } else {
                                            events.addToSelectedTransactions(listItem.transactionId)
                                        }
                                    } else {
                                        uiState.setIsInSelectionMode(it)
                                        events.addToSelectedTransactions(listItem.transactionId)
                                    }
                                },
                            ),
                        )
                    }
                }
                item {
                    NavigationBarSpacer()
                }
            }
        }
    }
}
