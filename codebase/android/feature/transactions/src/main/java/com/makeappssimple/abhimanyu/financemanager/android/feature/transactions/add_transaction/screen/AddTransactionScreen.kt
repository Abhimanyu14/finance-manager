package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModelImpl

@Composable
fun AddTransactionScreen(
    screenViewModel: AddOrEditTransactionScreenViewModel = hiltViewModel<AddOrEditTransactionScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AddTransactionScreen",
    )
    val uiState: AddOrEditTransactionScreenUiState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState by screenViewModel.uiVisibilityState.collectAsStateWithLifecycle()
    val isCtaButtonEnabled: Boolean by screenViewModel.isCtaButtonEnabled.collectAsStateWithLifecycle()
    val filteredCategories: List<Category> by screenViewModel.filteredCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val titleSuggestions: List<String> by screenViewModel.titleSuggestions.collectAsStateWithLifecycle()
    val transactionForValues: List<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val transactionTypesForNewTransaction: List<TransactionType> by screenViewModel.transactionTypesForNewTransaction.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val selectedTransactionType: TransactionType? by screenViewModel.selectedTransactionType.collectAsStateWithLifecycle()

    AddOrEditTransactionScreenView(
        data = AddOrEditTransactionScreenViewData(
            uiState = uiState,
            uiVisibilityState = uiVisibilityState,
            isCtaButtonEnabled = isCtaButtonEnabled,
            appBarTitleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
            filteredCategories = filteredCategories,
            sources = sources,
            titleSuggestions = titleSuggestions,
            transactionTypesForNewTransaction = transactionTypesForNewTransaction,
            transactionForValues = transactionForValues,
            currentTimeMillis = screenViewModel.currentTimeMillis,
            selectedTransactionType = selectedTransactionType,
            clearAmount = screenViewModel::clearAmount,
            clearDescription = screenViewModel::clearDescription,
            clearTitle = screenViewModel::clearTitle,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::insertTransaction,
            updateAmount = screenViewModel::updateAmount,
            updateCategory = screenViewModel::updateCategory,
            updateDescription = screenViewModel::updateDescription,
            updateSelectedTransactionForIndex = screenViewModel::updateSelectedTransactionForIndex,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
            updateSourceFrom = screenViewModel::updateSourceFrom,
            updateSourceTo = screenViewModel::updateSourceTo,
            updateTitle = screenViewModel::updateTitle,
            updateTransactionDate = screenViewModel::updateTransactionDate,
            updateTransactionTime = screenViewModel::updateTransactionTime,
        ),
        state = rememberCommonScreenViewState(),
    )
}
