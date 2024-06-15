package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.event.EditTransactionScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.rememberEditTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
public fun EditTransactionScreen(
    screenViewModel: EditTransactionScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditTransactionScreen",
    )

    // region view model data
    val uiState: EditTransactionScreenUiStateData by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiVisibilityState: EditTransactionScreenUiVisibilityState by screenViewModel.uiVisibilityState.collectAsStateWithLifecycle()
    val isCtaButtonEnabled: Boolean by screenViewModel.isCtaButtonEnabled.collectAsStateWithLifecycle()
    val filteredCategories: ImmutableList<Category> by screenViewModel.filteredCategories.collectAsStateWithLifecycle()
    val titleSuggestions: ImmutableList<String>? by screenViewModel.titleSuggestions.collectAsStateWithLifecycle()
    val selectedTransactionType: TransactionType? by screenViewModel.selectedTransactionType.collectAsStateWithLifecycle()
    val isDataFetchCompleted: Boolean by screenViewModel.isDataFetchCompleted.collectAsStateWithLifecycle()
    val validTransactionTypesForNewTransaction: ImmutableList<TransactionType> by screenViewModel.validTransactionTypesForNewTransaction.collectAsStateWithLifecycle()
    val currentLocalDate: LocalDate = screenViewModel.currentLocalDate
    val transactionForValues: ImmutableList<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle()
    val accounts: ImmutableList<Account> by screenViewModel.accounts.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndStateEvents = rememberEditTransactionScreenUIStateAndStateEvents(
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
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndStateEvents,
    ) {
        EditTransactionScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    EditTransactionScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
