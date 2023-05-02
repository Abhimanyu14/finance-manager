package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalOverscrollConfiguration
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
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.legend.Dot
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getReadableDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.adjustmentEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transferEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common.TransactionDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet.TransactionsFilterBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet.TransactionsSortBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption

internal enum class TransactionsBottomSheetType : BottomSheetType {
    NONE,
    FILTERS,
    SORT,
    DELETE_CONFIRMATION,
}

@Immutable
internal data class TransactionsScreenViewData(
    val isLoading: Boolean,
    val selectedFilter: Filter,
    val expenseCategories: List<Category>,
    val incomeCategories: List<Category>,
    val investmentCategories: List<Category>,
    val sortOptions: List<SortOption>,
    val sources: List<Source>,
    val transactionTypes: List<TransactionType>,
    val oldestTransactionTimestamp: Long,
    val transactionDetailsListItemViewData: Map<String, List<TransactionData>>,
    val searchText: String,
    val selectedSortOption: SortOption,
    val deleteTransaction: (transactionId: Int) -> Unit,
    val navigateToAddTransactionScreen: () -> Unit,
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
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
    var transactionIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BackHandler(
        enabled = data.searchText.isNotEmpty() || data.selectedFilter.areFiltersSelected(),
    ) {
        data.updateSearchText("")
        data.updateSelectedFilter(Filter())
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetShape = when (transactionsBottomSheetType) {
            TransactionsBottomSheetType.NONE,
            TransactionsBottomSheetType.SORT,
            TransactionsBottomSheetType.DELETE_CONFIRMATION,
            -> {
                BottomSheetShape
            }

            TransactionsBottomSheetType.FILTERS -> {
                BottomSheetExpandedShape
            }
        },
        sheetContent = {
            when (transactionsBottomSheetType) {
                TransactionsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                TransactionsBottomSheetType.FILTERS -> {
                    TransactionsFilterBottomSheetContent(
                        context = state.context,
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        expenseCategories = data.expenseCategories,
                        incomeCategories = data.incomeCategories,
                        investmentCategories = data.investmentCategories,
                        sources = data.sources,
                        transactionTypes = data.transactionTypes,
                        oldestTransactionTimestamp = data.oldestTransactionTimestamp,
                        selectedFilter = data.selectedFilter,
                        updateSelectedFilter = { updatedSelectedFilter ->
                            data.updateSelectedFilter(updatedSelectedFilter)
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
                        sortOptions = data.sortOptions.toList(),
                        selectedSortOptionIndex = data.sortOptions.indexOf(data.selectedSortOption),
                        updateSelectedSortOption = { index ->
                            data.updateSelectedSortOption(data.sortOptions[index])
                        },
                        resetBottomSheetType = {
                            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                        },
                    )
                }

                TransactionsBottomSheetType.DELETE_CONFIRMATION -> {
                    TransactionDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        transactionIdToDelete = transactionIdToDelete,
                        resetBottomSheetType = {
                            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
                        },
                        resetTransactionIdToDelete = {
                            transactionIdToDelete = null
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
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_transactions_appbar_title,
                navigationAction = data.navigateUp,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_transactions_floating_action_button_content_description,
                ),
                onClick = data.navigateToAddTransactionScreen,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = transactionsBottomSheetType != TransactionsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            transactionsBottomSheetType = TransactionsBottomSheetType.NONE
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AnimatedVisibility(
                visible = data.isLoading,
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = data.transactionDetailsListItemViewData.isNotEmpty() ||
                        data.searchText.isNotEmpty() ||
                        data.selectedFilter.areFiltersSelected()
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
                        Box {
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
                            if (data.selectedFilter.areFiltersSelected()) {
                                Dot(
                                    modifier = Modifier
                                        .align(
                                            alignment = Alignment.TopEnd,
                                        )
                                        .padding(
                                            all = 8.dp,
                                        ),
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                }
            }
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        bottom = 72.dp,
                    ),
                ) {
                    data.transactionDetailsListItemViewData.forEach { (date, listItemData) ->
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
                            val amountColor = listItem.transaction.getAmountTextColor()
                            val amountText =
                                if (listItem.transaction.transactionType == TransactionType.INCOME ||
                                    listItem.transaction.transactionType == TransactionType.EXPENSE ||
                                    listItem.transaction.transactionType == TransactionType.ADJUSTMENT ||
                                    listItem.transaction.transactionType == TransactionType.REFUND
                                ) {
                                    listItem.transaction.amount.toSignedString(
                                        isPositive = listItem.sourceTo.isNotNull(),
                                        isNegative = listItem.sourceFrom.isNotNull(),
                                    )
                                } else {
                                    listItem.transaction.amount.toString()
                                }
                            val dateAndTimeText = getReadableDateAndTime(
                                timestamp = listItem.transaction.transactionTimestamp,
                            )
                            val emoji = when (listItem.transaction.transactionType) {
                                TransactionType.TRANSFER -> {
                                    transferEmoji
                                }

                                TransactionType.ADJUSTMENT -> {
                                    adjustmentEmoji
                                }

                                else -> {
                                    listItem.category?.emoji
                                }
                            }.orEmpty()
                            val sourceFromName = listItem.sourceFrom?.name
                            val sourceToName = listItem.sourceTo?.name
                            val title = listItem.transaction.title
                            val transactionForText = listItem.transactionFor.titleToDisplay

                            TransactionListItem(
                                isDeleteButtonEnabled = false,
                                isDeleteButtonVisible = true,
                                isEditButtonVisible = false,
                                isExpanded = false,
                                isRefundButtonVisible = false,
                                amountColor = amountColor,
                                amountText = amountText,
                                dateAndTimeText = dateAndTimeText,
                                emoji = emoji,
                                sourceFromName = sourceFromName,
                                sourceToName = sourceToName,
                                title = title,
                                transactionForText = transactionForText,
                                onClick = {
                                    data.navigateToViewTransactionScreen(listItem.transaction.id)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
