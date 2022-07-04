package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel.EditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel.EditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel.EditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel.EditTransactionScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun EditTransactionScreen(
    screenViewModel: EditTransactionScreenViewModel = hiltViewModel<EditTransactionScreenViewModelImpl>(),
    transactionId: Int?,
) {
    logError(
        message = "Inside EditTransactionScreen",
    )
    val categories: List<Category> by screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val transactionTypesForNewTransaction: List<TransactionType> by screenViewModel.transactionTypesForNewTransaction.collectAsState(
        initial = emptyList(),
    )
    val uiState: EditTransactionScreenUiState by screenViewModel.uiState.collectAsState()
    val uiVisibilityState: EditTransactionScreenUiVisibilityState by screenViewModel.uiVisibilityState.collectAsState()
    val selectedTransactionType: TransactionType? by screenViewModel.selectedTransactionType.collectAsState()
    val isValidTransactionData: Boolean by screenViewModel.isValidTransactionData.collectAsState()
    val titleSuggestions: List<String> by screenViewModel.titleSuggestions.collectAsState()

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
        state = rememberEditTransactionScreenViewState(),
    )
}
