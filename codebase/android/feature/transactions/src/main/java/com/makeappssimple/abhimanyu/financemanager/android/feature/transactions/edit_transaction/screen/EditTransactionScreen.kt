package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenViewModel

@Composable
public fun EditTransactionScreen(
    screenViewModel: EditTransactionScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditTransactionScreen",
    )

    val screenUIData: MyResult<EditTransactionScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberEditTransactionScreenUIStateAndEvents(
        data = screenUIData,
        isEdit = true,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: EditTransactionScreenUIEvent ->
            when (uiEvent) {
                is EditTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is EditTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is EditTransactionScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateTransaction()
                }

                is EditTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                    viewModel.clearAmount()
                }

                is EditTransactionScreenUIEvent.OnClearDescriptionButtonClick -> {
                    viewModel.clearDescription()
                }

                is EditTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is EditTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        EditTransactionScreenBottomSheetType.SelectAccountFrom
                    )
                }

                is EditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        EditTransactionScreenBottomSheetType.SelectAccountTo
                    )
                }

                is EditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(true)
                }

                is EditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(true)
                }

                is EditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        EditTransactionScreenBottomSheetType.SelectCategory
                    )
                }

                is EditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is EditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                    viewModel.updateAccountFrom(
                        updatedAccountFrom = uiEvent.updatedAccountFrom,
                    )
                }

                is EditTransactionScreenUIEvent.OnAccountToUpdated -> {
                    viewModel.updateAccountTo(
                        updatedAccountTo = uiEvent.updatedAccountTo,
                    )
                }

                is EditTransactionScreenUIEvent.OnAmountUpdated -> {
                    viewModel.updateAmount(
                        updatedAmount = uiEvent.updatedAmount,
                    )
                }

                is EditTransactionScreenUIEvent.OnCategoryUpdated -> {
                    viewModel.updateCategory(
                        updatedCategory = uiEvent.updatedCategory,
                    )
                }

                is EditTransactionScreenUIEvent.OnDescriptionUpdated -> {
                    viewModel.updateDescription(
                        updatedDescription = uiEvent.updatedDescription,
                    )
                }

                is EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                    viewModel.updateSelectedTransactionForIndex(
                        updatedSelectedTransactionForIndex = uiEvent.updatedSelectedTransactionForIndex,
                    )
                }

                is EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                    )
                }

                is EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }

                is EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is EditTransactionScreenUIEvent.OnTitleUpdated -> {
                    viewModel.updateTitle(
                        updatedTitle = uiEvent.updatedTitle,
                    )
                }

                is EditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                    viewModel.updateTransactionDate(
                        updatedTransactionDate = uiEvent.updatedTransactionDate,
                    )
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is EditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                    viewModel.updateTransactionTime(
                        updatedTransactionTime = uiEvent.updatedTransactionTime,
                    )
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    EditTransactionScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
