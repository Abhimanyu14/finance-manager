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
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        TransactionsScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    TransactionsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
