package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.rememberAddOrEditTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModel

@Composable
public fun AddTransactionScreen(
    screenViewModel: AddOrEditTransactionScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionScreen",
    )

    val screenUIData: MyResult<AddOrEditTransactionScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberAddOrEditTransactionScreenUIState(
        data = screenUIData,
        isEdit = false,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AddOrEditTransactionScreenUIEvent ->
            when (uiEvent) {
                is AddOrEditTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                    uiState.resetScreenBottomSheetType()
                }

                is AddOrEditTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                }

                is AddOrEditTransactionScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertTransaction()
                }

                is AddOrEditTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                    viewModel.clearAmount()
                }

                is AddOrEditTransactionScreenUIEvent.OnClearDescriptionButtonClick -> {
                    viewModel.clearDescription()
                }

                is AddOrEditTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddOrEditTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                    uiState.setScreenBottomSheetType(
                        AddOrEditTransactionScreenBottomSheetType.SelectAccountFrom
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                    uiState.setScreenBottomSheetType(
                        AddOrEditTransactionScreenBottomSheetType.SelectAccountTo
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                    uiState.setIsTransactionTimePickerDialogVisible(true)
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                    uiState.setIsTransactionDatePickerDialogVisible(true)
                }

                is AddOrEditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                    uiState.setScreenBottomSheetType(
                        AddOrEditTransactionScreenBottomSheetType.SelectCategory
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddOrEditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                    viewModel.updateAccountFrom(
                        updatedAccountFrom = uiEvent.updatedAccountFrom,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnAccountToUpdated -> {
                    viewModel.updateAccountTo(
                        updatedAccountTo = uiEvent.updatedAccountTo,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnAmountUpdated -> {
                    viewModel.updateAmount(
                        updatedAmount = uiEvent.updatedAmount,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnCategoryUpdated -> {
                    viewModel.updateCategory(
                        updatedCategory = uiEvent.updatedCategory,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnDescriptionUpdated -> {
                    viewModel.updateDescription(
                        updatedDescription = uiEvent.updatedDescription,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                    viewModel.updateSelectedTransactionForIndex(
                        updatedSelectedTransactionForIndex = uiEvent.updatedSelectedTransactionForIndex,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                    uiState.setIsTransactionTimePickerDialogVisible(false)
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                    uiState.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddOrEditTransactionScreenUIEvent.OnTitleUpdated -> {
                    viewModel.updateTitle(
                        updatedTitle = uiEvent.updatedTitle,
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                    viewModel.updateTransactionDate(
                        updatedTransactionDate = uiEvent.updatedTransactionDate,
                    )
                    uiState.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                    viewModel.updateTransactionTime(
                        updatedTransactionTime = uiEvent.updatedTransactionTime,
                    )
                    uiState.setIsTransactionTimePickerDialogVisible(false)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AddOrEditTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
