package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet.TransactionsFilterBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet.TransactionsSortBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import java.time.LocalDate

enum class TransactionsBottomSheetType : BottomSheetType {
    FILTERS,
    NONE,
    SORT,
}

@Immutable
data class TransactionsScreenUIData(
    val isLoading: Boolean = false,
    val selectedFilter: Filter = Filter(),
    val sortOptions: List<SortOption> = emptyList(),
    val transactionTypes: List<TransactionType> = emptyList(),
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val currentTimeMillis: Long = 0L,
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> = emptyMap(),
    val searchText: String = "",
    val selectedSortOption: SortOption = SortOption.LATEST_FIRST,
)

@Immutable
internal data class TransactionsScreenUIEvents(
    val getExpenseCategories: () -> List<Category>,
    val getIncomeCategories: () -> List<Category>,
    val getInvestmentCategories: () -> List<Category>,
    val getSources: () -> List<Source>,
    val navigateToAddTransactionScreen: () -> Unit,
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    val updateSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Unit,
)

@Composable
internal fun TransactionsScreenUI(
    events: TransactionsScreenUIEvents,
    uiState: TransactionsScreenUIState,
    state: CommonScreenUIState,
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        bottomSheetType = uiState.transactionsBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    BackHandler(
        enabled = uiState.searchText.isNotEmpty() || uiState.selectedFilter.areFiltersSelected(),
    ) {
        events.updateSearchText("")
        events.updateSelectedFilter(Filter())
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetShape = when (uiState.transactionsBottomSheetType) {
            TransactionsBottomSheetType.NONE,
            TransactionsBottomSheetType.SORT,
            -> {
                BottomSheetShape
            }

            TransactionsBottomSheetType.FILTERS -> {
                BottomSheetExpandedShape
            }
        },
        sheetContent = {
            when (uiState.transactionsBottomSheetType) {
                TransactionsBottomSheetType.FILTERS -> {
                    TransactionsFilterBottomSheetContent(
                        context = state.context,
                        expenseCategories = events.getExpenseCategories(),
                        incomeCategories = events.getIncomeCategories(),
                        investmentCategories = events.getInvestmentCategories(),
                        sources = events.getSources(),
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

                TransactionsBottomSheetType.SORT -> {
                    TransactionsSortBottomSheetContent(
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
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_transactions_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_transactions_floating_action_button_content_description,
                ),
                onClick = events.navigateToAddTransactionScreen,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AnimatedVisibility(
                visible = uiState.isLoading,
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = uiState.transactionDetailsListItemViewData.isNotEmpty() ||
                        uiState.searchText.isNotEmpty() ||
                        uiState.selectedFilter.areFiltersSelected()
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
                                uiState.setTransactionsBottomSheetType(TransactionsBottomSheetType.SORT)
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
                                uiState.setTransactionsBottomSheetType(TransactionsBottomSheetType.FILTERS)
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
                        TransactionListItem(
                            data = listItem,
                            events = TransactionListItemEvents(
                                onClick = {
                                    events.navigateToViewTransactionScreen(listItem.transactionId)
                                },
                            ),
                        )
                    }
                }
            }
        }
    }
}
