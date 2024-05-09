package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_TRANSACTIONS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_TRANSACTIONS
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.SelectTransactionForBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.SelectTransactionForBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.SelectTransactionForBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionsFilterBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionsMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionsMenuBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionsSortBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MySearchBarEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MySelectionModeTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

@Composable
internal fun TransactionsScreenUI(
    uiState: TransactionsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: TransactionsScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != TransactionsScreenBottomSheetType.None,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
    )

    BackHandler(
        // TODO(Abhi): Move this logic outside the UI composable
        enabled = uiState.searchText.isNotEmpty() ||
                uiState.selectedFilter.areFiltersSelected() ||
                uiState.isInSelectionMode,
    ) {
        handleUIEvent(TransactionsScreenUIEvent.OnNavigationBackButtonClick)
    }

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_TRANSACTIONS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                TransactionsScreenBottomSheetType.Filters -> {
                    TransactionsFilterBottomSheet(
                        expenseCategories = uiState.expenseCategories,
                        incomeCategories = uiState.incomeCategories,
                        investmentCategories = uiState.investmentCategories,
                        accounts = uiState.accounts,
                        transactionForValues = uiState.transactionForValues,
                        transactionTypes = uiState.transactionTypes,
                        defaultMinDate = uiState.oldestTransactionLocalDate,
                        defaultMaxDate = uiState.currentLocalDate,
                        selectedFilter = uiState.selectedFilter,
                        updateSelectedFilter = { updatedSelectedFilter ->
                            handleUIEvent(
                                TransactionsScreenUIEvent.OnSelectedFilterUpdated(
                                    updatedSelectedFilter = updatedSelectedFilter,
                                )
                            )
                        },
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
                    )
                }

                TransactionsScreenBottomSheetType.Menu -> {
                    TransactionsMenuBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                is TransactionsMenuBottomSheetEvent.OnSelectAllTransactionsClick -> {
                                    handleUIEvent(TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick)
                                }

                                is TransactionsMenuBottomSheetEvent.OnUpdateTransactionForClick -> {
                                    uiState.setScreenBottomSheetType(
                                        TransactionsScreenBottomSheetType.SelectTransactionFor
                                    )
                                }

                                is TransactionsMenuBottomSheetEvent.ResetBottomSheetType -> {
                                    uiState.resetScreenBottomSheetType()
                                }
                            }
                        },
                    )
                }

                TransactionsScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                TransactionsScreenBottomSheetType.SelectTransactionFor -> {
                    SelectTransactionForBottomSheet(
                        data = SelectTransactionForBottomSheetData(
                            transactionForValues = uiState.transactionForValues,
                        ),
                        events = SelectTransactionForBottomSheetEvents(
                            onItemClick = {
                                uiState.setIsInSelectionMode(false)
                                handleUIEvent(
                                    TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick(
                                        updatedTransactionForValues = it.id,
                                    )
                                )
                                uiState.resetScreenBottomSheetType()
                            },
                        ),
                    )
                }

                TransactionsScreenBottomSheetType.Sort -> {
                    TransactionsSortBottomSheet(
                        selectedSortOptionIndex = uiState.sortOptions.indexOf(uiState.selectedSortOption),
                        sortOptions = uiState.sortOptions.toList(),
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
                        updateSelectedSortOption = { index ->
                            handleUIEvent(
                                TransactionsScreenUIEvent.OnSelectedSortOptionUpdated(
                                    updatedSelectedSortOption = uiState.sortOptions[index],
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        sheetShape = when (uiState.screenBottomSheetType) {
            is TransactionsScreenBottomSheetType.Menu,
            is TransactionsScreenBottomSheetType.None,
            is TransactionsScreenBottomSheetType.SelectTransactionFor,
            is TransactionsScreenBottomSheetType.Sort,
            -> {
                BottomSheetShape
            }

            is TransactionsScreenBottomSheetType.Filters -> {
                BottomSheetExpandedShape
            }
        },
        topBar = {
            if (uiState.isInSelectionMode) {
                MySelectionModeTopAppBar(
                    appBarActions = {
                        MyIconButton(
                            tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = MyIcons.MoreVert,
                            contentDescriptionStringResourceId = R.string.screen_transactions_selection_mode_appbar_menu_more_options,
                            onClick = {
                                uiState.setScreenBottomSheetType(
                                    TransactionsScreenBottomSheetType.Menu
                                )
                            },
                        )
                    },
                    navigationAction = {
                        handleUIEvent(TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick)
                    },
                    title = {
                        MyText(
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
                    navigationAction = {
                        handleUIEvent(TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                    },
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
                        .navigationBarsSpacer(),
                    iconImageVector = MyIcons.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_transactions_floating_action_button_content_description,
                    ),
                    onClick = {
                        handleUIEvent(TransactionsScreenUIEvent.OnFloatingActionButtonClick)
                    },
                )
            }
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != TransactionsScreenBottomSheetType.None,
        backHandlerEnabled = uiState.screenBottomSheetType != TransactionsScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_TRANSACTIONS,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            AnimatedVisibility(
                visible = uiState.isLoading,
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                // TODO(Abhi): Move logic to UI state
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
                            handleEvent = { events ->
                                when (events) {
                                    is MySearchBarEvent.OnSearch -> {
                                        state.focusManager.clearFocus()
                                    }

                                    is MySearchBarEvent.OnSearchTextChange -> {
                                        handleUIEvent(
                                            TransactionsScreenUIEvent.OnSearchTextUpdated(
                                                updatedSearchText = events.updatedSearchText,
                                            )
                                        )
                                    }
                                }
                            },
                        )
                    }
                    ActionButton(
                        data = ActionButtonData(
                            imageVector = MyIcons.SwapVert,
                            contentDescriptionStringResourceId = R.string.screen_transactions_sort_button_content_description,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is ActionButtonEvent.OnClick -> {
                                    uiState.setScreenBottomSheetType(
                                        TransactionsScreenBottomSheetType.Sort
                                    )
                                }
                            }
                        },
                    )
                    ActionButton(
                        data = ActionButtonData(
                            isIndicatorVisible = uiState.selectedFilter.areFiltersSelected(),
                            imageVector = MyIcons.FilterAlt,
                            contentDescriptionStringResourceId = R.string.screen_transactions_filter_button_content_description,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is ActionButtonEvent.OnClick -> {
                                    uiState.setScreenBottomSheetType(
                                        TransactionsScreenBottomSheetType.Filters
                                    )
                                }
                            }
                        },
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
                            listItem.transactionId
                        },
                    ) { _, listItem ->
                        val isSelected =
                            uiState.selectedTransactions.contains(listItem.transactionId)
                        TransactionListItem(
                            data = listItem.copy(
                                isInSelectionMode = uiState.isInSelectionMode,
                                isSelected = isSelected,
                            ),
                            handleEvent = {
                                when (it) {
                                    is TransactionListItemEvent.OnClick -> {
                                        handleUIEvent(
                                            TransactionsScreenUIEvent.OnTransactionListItem.Click(
                                                isInSelectionMode = uiState.isInSelectionMode,
                                                isSelected = isSelected,
                                                transactionId = listItem.transactionId,
                                            )
                                        )
                                    }

                                    is TransactionListItemEvent.OnDeleteButtonClick -> {}

                                    is TransactionListItemEvent.OnEditButtonClick -> {}

                                    is TransactionListItemEvent.OnLongClick -> {
                                        handleUIEvent(
                                            TransactionsScreenUIEvent.OnTransactionListItem.LongClick(
                                                isInSelectionMode = uiState.isInSelectionMode,
                                                isSelected = isSelected,
                                                transactionId = listItem.transactionId,
                                            )
                                        )
                                    }

                                    is TransactionListItemEvent.OnRefundButtonClick -> {}
                                }
                            },
                        )
                    }
                }
                item {
                    NavigationBarsAndImeSpacer()
                }
            }
        }
    }
}
