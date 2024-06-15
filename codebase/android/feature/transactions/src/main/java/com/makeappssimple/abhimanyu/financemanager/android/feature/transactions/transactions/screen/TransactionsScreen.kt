package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
public fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside TransactionsScreen",
    )

    // region view model data
    val allTransactionData: ImmutableList<TransactionData> by screenViewModel.allTransactionData.collectAsStateWithLifecycle()
    val expenseCategories: ImmutableList<Category>? by screenViewModel.expenseCategories.collectAsStateWithLifecycle()
    val incomeCategories: ImmutableList<Category>? by screenViewModel.incomeCategories.collectAsStateWithLifecycle()
    val investmentCategories: ImmutableList<Category>? by screenViewModel.investmentCategories.collectAsStateWithLifecycle()
    val accounts: ImmutableList<Account>? by screenViewModel.accounts.collectAsStateWithLifecycle()
    val transactionForValues: ImmutableList<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle()
    val transactionTypes: ImmutableList<TransactionType> = screenViewModel.transactionTypes
    val oldestTransactionLocalDate: LocalDate? by screenViewModel.oldestTransactionLocalDate.collectAsStateWithLifecycle()
    val sortOptions: ImmutableList<SortOption> = screenViewModel.sortOptions
    val currentLocalDate: LocalDate = screenViewModel.currentLocalDate
    // endregion

    val uiStateAndStateEvents = rememberTransactionsScreenUIStateAndEvents(
        allTransactionData = allTransactionData,
        expenseCategories = expenseCategories.orEmpty(),
        incomeCategories = incomeCategories.orEmpty(),
        investmentCategories = investmentCategories.orEmpty(),
        accounts = accounts.orEmpty(),
        transactionForValues = transactionForValues,
        transactionTypes = transactionTypes,
        oldestTransactionLocalDate = oldestTransactionLocalDate,
        sortOptions = sortOptions,
        currentLocalDate = currentLocalDate,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndStateEvents,
    ) {
        TransactionsScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    TransactionsScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
