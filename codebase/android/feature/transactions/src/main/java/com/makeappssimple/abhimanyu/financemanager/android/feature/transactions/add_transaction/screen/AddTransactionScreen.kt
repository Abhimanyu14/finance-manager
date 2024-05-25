package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel

@Composable
public fun AddTransactionScreen(
    screenViewModel: AddTransactionScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionScreen",
    )

    val screenUIData: MyResult<AddTransactionScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberAddTransactionScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddTransactionScreenUIEvent ->
            when (uiEvent) {
                is AddTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddTransactionScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertTransaction()
                }

                is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                    viewModel.clearAmount()
                }

                is AddTransactionScreenUIEvent.OnClearDescriptionButtonClick -> {
                    viewModel.clearDescription()
                }

                is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddTransactionScreenBottomSheetType.SelectAccountFrom
                    )
                }

                is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddTransactionScreenBottomSheetType.SelectAccountTo
                    )
                }

                is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(true)
                }

                is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(true)
                }

                is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddTransactionScreenBottomSheetType.SelectCategory
                    )
                }

                is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                    viewModel.updateAccountFrom(
                        updatedAccountFrom = uiEvent.updatedAccountFrom,
                    )
                }

                is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                    viewModel.updateAccountTo(
                        updatedAccountTo = uiEvent.updatedAccountTo,
                    )
                }

                is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                    viewModel.updateAmount(
                        updatedAmount = uiEvent.updatedAmount,
                    )
                }

                is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                    viewModel.updateCategory(
                        updatedCategory = uiEvent.updatedCategory,
                    )
                }

                is AddTransactionScreenUIEvent.OnDescriptionUpdated -> {
                    viewModel.updateDescription(
                        updatedDescription = uiEvent.updatedDescription,
                    )
                }

                is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                    viewModel.updateSelectedTransactionForIndex(
                        updatedSelectedTransactionForIndex = uiEvent.updatedSelectedTransactionForIndex,
                    )
                }

                is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                    )
                }

                is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                    viewModel.updateTitle(
                        updatedTitle = uiEvent.updatedTitle,
                    )
                }

                is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                    viewModel.updateTransactionDate(
                        updatedTransactionDate = uiEvent.updatedTransactionDate,
                    )
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
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

    AddTransactionScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
