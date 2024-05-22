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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.rememberAddOrEditTransactionScreenUIStateAndEvents
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

    val screenUIData: MyResult<AddOrEditTransactionScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberAddOrEditTransactionScreenUIStateAndEvents(
        data = screenUIData,
        isEdit = false,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddOrEditTransactionScreenUIEvent ->
            when (uiEvent) {
                is AddOrEditTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddOrEditTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
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
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddOrEditTransactionScreenBottomSheetType.SelectAccountFrom
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddOrEditTransactionScreenBottomSheetType.SelectAccountTo
                    )
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(true)
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(true)
                }

                is AddOrEditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
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
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
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
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddOrEditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
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

    AddOrEditTransactionScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
