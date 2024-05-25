package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
public fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside TransactionsScreen",
    )

    // region view model data
    val allTransactionData: ImmutableList<TransactionData> by viewModel.allTransactionData.collectAsStateWithLifecycle()
    val expenseCategories: ImmutableList<Category>? by viewModel.expenseCategories.collectAsStateWithLifecycle()
    val incomeCategories: ImmutableList<Category>? by viewModel.incomeCategories.collectAsStateWithLifecycle()
    val investmentCategories: ImmutableList<Category>? by viewModel.investmentCategories.collectAsStateWithLifecycle()
    val accounts: ImmutableList<Account>? by viewModel.accounts.collectAsStateWithLifecycle()
    val transactionForValues: ImmutableList<TransactionFor> by viewModel.transactionForValues.collectAsStateWithLifecycle()
    val transactionTypes: ImmutableList<TransactionType> = viewModel.transactionTypes
    val oldestTransactionLocalDate: LocalDate? by viewModel.oldestTransactionLocalDate.collectAsStateWithLifecycle()
    val sortOptions: ImmutableList<SortOption> = viewModel.sortOptions
    val currentLocalDate: LocalDate = viewModel.currentLocalDate
    // endregion

    val uiStateAndEvents = rememberTransactionsScreenUIStateAndEvents(
        allTransactionData = allTransactionData,
        expenseCategories = expenseCategories,
        incomeCategories = incomeCategories,
        investmentCategories = investmentCategories,
        accounts = accounts,
        transactionForValues = transactionForValues,
        transactionTypes = transactionTypes,
        oldestTransactionLocalDate = oldestTransactionLocalDate,
        sortOptions = sortOptions,
        currentLocalDate = currentLocalDate,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: TransactionsScreenUIEvent ->
            when (uiEvent) {
                is TransactionsScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        TransactionsScreenBottomSheetType.Menu
                    )
                }

                is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                    uiStateAndEvents.events.setIsInSelectionMode(false)
                    uiStateAndEvents.events.clearSelectedTransactions()
                }

                is TransactionsScreenUIEvent.OnFilterActionButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        TransactionsScreenBottomSheetType.Filters
                    )
                }

                is TransactionsScreenUIEvent.OnSortActionButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        TransactionsScreenBottomSheetType.Sort
                    )
                }

                is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.setSearchText("")
                    uiStateAndEvents.events.setSelectedFilter(Filter())
                    uiStateAndEvents.events.setIsInSelectionMode(false)
                    uiStateAndEvents.events.clearSelectedTransactions()
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is TransactionsScreenUIEvent.OnTransactionListItem.Click -> {
                    if (uiEvent.isInSelectionMode) {
                        if (uiEvent.isSelected) {
                            uiStateAndEvents.events.removeFromSelectedTransactions(uiEvent.transactionId)
                        } else {
                            uiStateAndEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                        }
                    } else {
                        viewModel.navigateToViewTransactionScreen(
                            transactionId = uiEvent.transactionId,
                        )
                    }
                }

                is TransactionsScreenUIEvent.OnTransactionListItem.LongClick -> {
                    if (uiEvent.isInSelectionMode) {
                        if (uiEvent.isSelected) {
                            uiStateAndEvents.events.removeFromSelectedTransactions(uiEvent.transactionId)
                        } else {
                            uiStateAndEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                        }
                    } else {
                        uiStateAndEvents.events.setIsInSelectionMode(true)
                        uiStateAndEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                    }
                }

                is TransactionsScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddTransactionScreen()
                }

                is TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.selectAllTransactions()
                }

                is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        TransactionsScreenBottomSheetType.SelectTransactionFor
                    )
                }

                is TransactionsScreenUIEvent.OnSearchTextUpdated -> {
                    uiStateAndEvents.events.setSearchText(uiEvent.updatedSearchText)
                }

                is TransactionsScreenUIEvent.OnSelectedFilterUpdated -> {
                    uiStateAndEvents.events.setSelectedFilter(uiEvent.updatedSelectedFilter)
                }

                is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                    uiStateAndEvents.events.setSelectedSortOption(uiEvent.updatedSelectedSortOption)
                }

                is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                    uiStateAndEvents.events.setIsInSelectionMode(false)
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.updateTransactionForValuesInTransactions(
                        selectedTransactions = uiStateAndEvents.state.selectedTransactions,
                        transactionForId = uiEvent.updatedTransactionForValues,
                    )
                    uiStateAndEvents.events.clearSelectedTransactions()
                }
            }
        }
    }

    TransactionsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
