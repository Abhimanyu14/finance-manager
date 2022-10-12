package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModelImpl

@Composable
fun AddTransactionScreen(
    screenViewModel: AddTransactionScreenViewModel = hiltViewModel<AddTransactionScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddTransactionScreen",
    )
    val uiVisibilityState: AddTransactionScreenUiVisibilityState by screenViewModel.uiVisibilityState.collectAsStateWithLifecycle()
    val uiState: AddTransactionScreenUiState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val isValidTransactionData: Boolean by screenViewModel.isValidTransactionData.collectAsStateWithLifecycle()
    val filteredCategories: List<Category> by screenViewModel.filteredCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val transactionForValues: List<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val titleSuggestions: List<String> by screenViewModel.titleSuggestions.collectAsStateWithLifecycle()
    val transactionTypesForNewTransaction: List<TransactionType> by screenViewModel.transactionTypesForNewTransaction.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val selectedTransactionType: TransactionType? by screenViewModel.selectedTransactionType.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddTransactionScreenView(
        data = AddTransactionScreenViewData(
            uiState = uiState,
            uiVisibilityState = uiVisibilityState,
            isValidTransactionData = isValidTransactionData,
            filteredCategories = filteredCategories,
            sources = sources,
            titleSuggestions = titleSuggestions,
            transactionTypesForNewTransaction = transactionTypesForNewTransaction,
            transactionForValues = transactionForValues,
            navigationManager = screenViewModel.navigationManager,
            selectedTransactionType = selectedTransactionType,
            clearAmount = screenViewModel::clearAmount,
            clearDescription = screenViewModel::clearDescription,
            clearTitle = screenViewModel::clearTitle,
            insertTransaction = screenViewModel::insertTransaction,
            updateAmount = { updatedAmount ->
                screenViewModel.updateAmount(
                    updatedAmount = updatedAmount,
                )
            },
            updateCategory = { updatedCategory ->
                screenViewModel.updateCategory(
                    updatedCategory = updatedCategory,
                )
            },
            updateDescription = { updatedDescription ->
                screenViewModel.updateDescription(
                    updatedDescription = updatedDescription,
                )
            },
            updateSelectedTransactionForIndex = { updatedSelectedTransactionForIndex ->
                screenViewModel.updateSelectedTransactionForIndex(
                    updatedSelectedTransactionForIndex = updatedSelectedTransactionForIndex,
                )
            },
            updateSelectedTransactionTypeIndex = { updatedSelectedTransactionTypeIndex ->
                screenViewModel.updateSelectedTransactionTypeIndex(
                    updatedSelectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
                )
            },
            updateSourceFrom = { updatedSourceFrom ->
                screenViewModel.updateSourceFrom(
                    updatedSourceFrom = updatedSourceFrom,
                )
            },
            updateSourceTo = { updatedSourceTo ->
                screenViewModel.updateSourceTo(
                    updatedSourceTo = updatedSourceTo,
                )
            },
            updateTitle = { updatedTitle ->
                screenViewModel.updateTitle(
                    updatedTitle = updatedTitle,
                )
            },
            updateTransactionCalendar = { updatedTransactionCalendar ->
                screenViewModel.updateTransactionCalendar(
                    updatedTransactionCalendar = updatedTransactionCalendar,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
