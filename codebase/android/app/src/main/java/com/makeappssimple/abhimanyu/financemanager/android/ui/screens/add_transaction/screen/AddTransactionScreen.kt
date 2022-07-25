package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.viewmodel.AddTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.viewmodel.AddTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.viewmodel.AddTransactionScreenViewModelImpl

@Composable
fun AddTransactionScreen(
    screenViewModel: AddTransactionScreenViewModel = hiltViewModel<AddTransactionScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddTransactionScreen",
    )
    val uiVisibilityState: AddTransactionScreenUiVisibilityState by screenViewModel.uiVisibilityState.collectAsState()
    val uiState: AddTransactionScreenUiState by screenViewModel.uiState.collectAsState()
    val isValidTransactionData: Boolean by screenViewModel.isValidTransactionData.collectAsState()
    val categories: List<Category> by screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val titleSuggestions: List<String> by screenViewModel.titleSuggestions.collectAsState()
    val transactionTypesForNewTransaction: List<TransactionType> by screenViewModel.transactionTypesForNewTransaction.collectAsState(
        initial = emptyList(),
    )
    val selectedTransactionType: TransactionType? by screenViewModel.selectedTransactionType.collectAsState()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddTransactionScreenView(
        data = AddTransactionScreenViewData(
            transactionForValues = screenViewModel.transactionForValues.toList(),
            uiState = uiState,
            uiVisibilityState = uiVisibilityState,
            isValidTransactionData = isValidTransactionData,
            categories = categories,
            sources = sources,
            titleSuggestions = titleSuggestions,
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
            insertTransaction = {
                screenViewModel.insertTransaction()
            },
            updateAmount = { updatedAmount ->
                screenViewModel.updateAmount(updatedAmount)
            },
            updateCategory = { updatedCategory ->
                screenViewModel.updateCategory(updatedCategory)
            },
            updateDescription = { updatedDescription ->
                screenViewModel.updateDescription(updatedDescription)
            },
            updateSelectedTransactionForIndex = { updatedSelectedTransactionForIndex ->
                screenViewModel.updateSelectedTransactionForIndex(updatedSelectedTransactionForIndex)
            },
            updateSelectedTransactionTypeIndex = { updatedSelectedTransactionTypeIndex ->
                screenViewModel.updateSelectedTransactionTypeIndex(
                    updatedSelectedTransactionTypeIndex
                )
            },
            updateSourceFrom = { updatedSourceFrom ->
                screenViewModel.updateSourceFrom(updatedSourceFrom)
            },
            updateSourceTo = { updatedSourceTo ->
                screenViewModel.updateSourceTo(updatedSourceTo)
            },
            updateTitle = { updatedTitle ->
                screenViewModel.updateTitle(updatedTitle)
            },
            updateTransactionCalendar = { updatedTransactionCalendar ->
                screenViewModel.updateTransactionCalendar(updatedTransactionCalendar)
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
