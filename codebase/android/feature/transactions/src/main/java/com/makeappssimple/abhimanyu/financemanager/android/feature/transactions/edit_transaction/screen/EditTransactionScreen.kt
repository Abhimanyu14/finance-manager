package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModelImpl

@Composable
fun EditTransactionScreen(
    screenViewModel: AddOrEditTransactionScreenViewModel = hiltViewModel<AddOrEditTransactionScreenViewModelImpl>(),
) {
    logError(
        message = "Inside EditTransactionScreen",
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

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddOrEditTransactionScreenView(
        data = AddOrEditTransactionScreenViewData(
            uiState = uiState,
            uiVisibilityState = uiVisibilityState,
            isCtaButtonEnabled = isCtaButtonEnabled,
            appBarTitleTextStringResourceId = R.string.screen_edit_transaction_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_floating_action_button_content_description,
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
            onCtaButtonClick = screenViewModel::updateTransaction,
            updateAmount = screenViewModel::updateAmount,
            updateCategory = screenViewModel::updateCategory,
            updateDescription = screenViewModel::updateDescription,
            updateSelectedTransactionForIndex = screenViewModel::updateSelectedTransactionForIndex,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
            updateSourceFrom = screenViewModel::updateSourceFrom,
            updateSourceTo = screenViewModel::updateSourceTo,
            updateTitle = screenViewModel::updateTitle,
            updateTransactionCalendar = screenViewModel::updateTransactionCalendar,
        ),
        state = rememberCommonScreenViewState(),
    )
}
