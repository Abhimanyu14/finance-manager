package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenViewModelImpl

@Composable
fun EditTransactionScreen(
    screenViewModel: EditTransactionScreenViewModel = hiltViewModel<EditTransactionScreenViewModelImpl>(),
    transactionId: Int?,
) {
    logError(
        message = "Inside EditTransactionScreen",
    )
    val categories: List<Category> by screenViewModel.categories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val transactionTypesForNewTransaction: List<TransactionType> by screenViewModel.transactionTypesForNewTransaction.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val uiState: EditTransactionScreenUiState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiVisibilityState: EditTransactionScreenUiVisibilityState by screenViewModel.uiVisibilityState.collectAsStateWithLifecycle()
    val selectedTransactionType: TransactionType? by screenViewModel.selectedTransactionType.collectAsStateWithLifecycle()
    val isValidTransactionData: Boolean by screenViewModel.isValidTransactionData.collectAsStateWithLifecycle()
    val titleSuggestions: List<String> by screenViewModel.titleSuggestions.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    EditTransactionScreenView(
        data = EditTransactionScreenViewData(
            isValidTransactionData = isValidTransactionData,
            uiState = uiState,
            uiVisibilityState = uiVisibilityState,
            transactionId = transactionId,
            categories = categories,
            sources = sources,
            titleSuggestions = titleSuggestions,
            transactionForValues = screenViewModel.transactionForValues.toList(),
            transactionTypesForNewTransaction = transactionTypesForNewTransaction,
            navigationManager = screenViewModel.navigationManager,
            selectedTransactionType = selectedTransactionType,
            clearAmount = {
                screenViewModel.clearAmount()
            },
            clearDescription = {
                screenViewModel.clearDescription()
            },
            clearTitle = {
                screenViewModel.clearTitle()
            },
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
            updateTransaction = {
                screenViewModel.updateTransaction()
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
