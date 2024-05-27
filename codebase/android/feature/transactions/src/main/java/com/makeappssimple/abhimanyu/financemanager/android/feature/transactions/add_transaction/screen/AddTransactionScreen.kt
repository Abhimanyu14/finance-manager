package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
public fun AddTransactionScreen(
    screenViewModel: AddTransactionScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionScreen",
    )

    // region view model data
    val uiState: AddTransactionScreenUiStateData by viewModel.uiState.collectAsStateWithLifecycle()
    val uiVisibilityState: AddTransactionScreenUiVisibilityState by viewModel.uiVisibilityState.collectAsStateWithLifecycle()
    val isCtaButtonEnabled: Boolean by viewModel.isCtaButtonEnabled.collectAsStateWithLifecycle()
    val filteredCategories: ImmutableList<Category> by viewModel.filteredCategories.collectAsStateWithLifecycle()
    val titleSuggestions: ImmutableList<String>? by viewModel.titleSuggestions.collectAsStateWithLifecycle()
    val selectedTransactionType: TransactionType? by viewModel.selectedTransactionType.collectAsStateWithLifecycle()
    val isDataFetchCompleted: Boolean by viewModel.isDataFetchCompleted.collectAsStateWithLifecycle()
    val validTransactionTypesForNewTransaction: ImmutableList<TransactionType> by viewModel.validTransactionTypesForNewTransaction.collectAsStateWithLifecycle()
    val currentLocalDate: LocalDate = viewModel.currentLocalDate
    val transactionForValues: ImmutableList<TransactionFor> by viewModel.transactionForValues.collectAsStateWithLifecycle()
    val accounts: ImmutableList<Account> by viewModel.accounts.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberAddTransactionScreenUIStateAndEvents(
        uiState = uiState,
        uiVisibilityState = uiVisibilityState,
        isCtaButtonEnabled = isCtaButtonEnabled,
        filteredCategories = filteredCategories,
        titleSuggestions = titleSuggestions,
        selectedTransactionType = selectedTransactionType,
        isDataFetchCompleted = isDataFetchCompleted,
        validTransactionTypesForNewTransaction = validTransactionTypesForNewTransaction,
        currentLocalDate = currentLocalDate,
        transactionForValues = transactionForValues,
        accounts = accounts,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddTransactionScreenUIEvent ->
            when (uiEvent) {
                is AddTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddTransactionScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertTransaction()
                }

                is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                    viewModel.clearAmount()
                }

                is AddTransactionScreenUIEvent.OnClearDescriptionButtonClick -> {
                    viewModel.clearDescription()
                }

                is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddTransactionScreenBottomSheetType.SelectAccountFrom
                    )
                }

                is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddTransactionScreenBottomSheetType.SelectAccountTo
                    )
                }

                is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(true)
                }

                is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(true)
                }

                is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddTransactionScreenBottomSheetType.SelectCategory
                    )
                }

                is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                    viewModel.updateAccountFrom(
                        updatedAccountFrom = uiEvent.updatedAccountFrom,
                    )
                }

                is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                    viewModel.updateAccountTo(
                        updatedAccountTo = uiEvent.updatedAccountTo,
                    )
                }

                is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                    viewModel.updateAmount(
                        updatedAmount = uiEvent.updatedAmount,
                    )
                }

                is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                    viewModel.updateCategory(
                        updatedCategory = uiEvent.updatedCategory,
                    )
                }

                is AddTransactionScreenUIEvent.OnDescriptionUpdated -> {
                    viewModel.updateDescription(
                        updatedDescription = uiEvent.updatedDescription,
                    )
                }

                is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                    viewModel.updateSelectedTransactionForIndex(
                        updatedSelectedTransactionForIndex = uiEvent.updatedSelectedTransactionForIndex,
                    )
                }

                is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                    )
                }

                is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                    viewModel.updateTitle(
                        updatedTitle = uiEvent.updatedTitle,
                    )
                }

                is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                    viewModel.updateTransactionDate(
                        updatedTransactionDate = uiEvent.updatedTransactionDate,
                    )
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                    viewModel.updateTransactionTime(
                        updatedTransactionTime = uiEvent.updatedTransactionTime,
                    )
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AddTransactionScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
