package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenInitialData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDateTime

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

    val dateTimeUtil = remember {
        DateTimeUtilImpl()
    }

    // region view model data
    val addTransactionScreenInitialData: AddTransactionScreenInitialData? by viewModel.addTransactionScreenInitialData.collectAsStateWithLifecycle()
    val titleSuggestions: ImmutableList<String>? by viewModel.titleSuggestions.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberAddTransactionScreenUIStateAndEvents(
        addTransactionScreenInitialData = addTransactionScreenInitialData,
        titleSuggestions = titleSuggestions,
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

                    val amountValue = uiStateAndEvents.state.uiState.amount.text.toLongOrZero()
                    val amount = Amount(
                        value = amountValue,
                    )

                    val categoryId = when (uiStateAndEvents.state.selectedTransactionType) {
                        TransactionType.INCOME -> {
                            uiStateAndEvents.state.uiState.category?.id
                        }

                        TransactionType.EXPENSE -> {
                            uiStateAndEvents.state.uiState.category?.id
                        }

                        TransactionType.TRANSFER -> {
                            null
                        }

                        TransactionType.ADJUSTMENT -> {
                            null
                        }

                        TransactionType.INVESTMENT -> {
                            uiStateAndEvents.state.uiState.category?.id
                        }

                        TransactionType.REFUND -> {
                            uiStateAndEvents.state.uiState.category?.id
                        }
                    }
                    val accountFromId = when (uiStateAndEvents.state.selectedTransactionType) {
                        TransactionType.INCOME -> {
                            null
                        }

                        TransactionType.EXPENSE -> {
                            uiStateAndEvents.state.uiState.accountFrom?.id
                        }

                        TransactionType.TRANSFER -> {
                            uiStateAndEvents.state.uiState.accountFrom?.id
                        }

                        TransactionType.ADJUSTMENT -> {
                            null
                        }

                        TransactionType.INVESTMENT -> {
                            uiStateAndEvents.state.uiState.accountFrom?.id
                        }

                        TransactionType.REFUND -> {
                            null
                        }
                    }
                    val accountToId = when (uiStateAndEvents.state.selectedTransactionType) {
                        TransactionType.INCOME -> {
                            uiStateAndEvents.state.uiState.accountTo?.id
                        }

                        TransactionType.EXPENSE -> {
                            null
                        }

                        TransactionType.TRANSFER -> {
                            uiStateAndEvents.state.uiState.accountTo?.id
                        }

                        TransactionType.ADJUSTMENT -> {
                            null
                        }

                        TransactionType.INVESTMENT -> {
                            null
                        }

                        TransactionType.REFUND -> {
                            uiStateAndEvents.state.uiState.accountTo?.id
                        }
                    }
                    val title = when (uiStateAndEvents.state.selectedTransactionType) {
                        TransactionType.TRANSFER -> {
                            TransactionType.TRANSFER.title
                        }

                        TransactionType.REFUND -> {
                            TransactionType.REFUND.title
                        }

                        else -> {
                            uiStateAndEvents.state.uiState.title.text.capitalizeWords()
                        }
                    }
                    val transactionForId: Int =
                        when (uiStateAndEvents.state.selectedTransactionType) {
                            TransactionType.INCOME -> {
                                1
                            }

                            TransactionType.EXPENSE -> {
                                uiStateAndEvents.state.uiState.selectedTransactionForIndex
                            }

                            TransactionType.TRANSFER -> {
                                1
                            }

                            TransactionType.ADJUSTMENT -> {
                                1
                            }

                            TransactionType.INVESTMENT -> {
                                1
                            }

                            TransactionType.REFUND -> {
                                1
                            }
                        }

                    val transactionTimestamp = LocalDateTime
                        .of(
                            uiStateAndEvents.state.uiState.transactionDate,
                            uiStateAndEvents.state.uiState.transactionTime
                        )
                        .toEpochMilli()

                    viewModel.insertTransaction(
                        amountValue = amountValue,
                        accountFrom = if (accountFromId.isNotNull()) {
                            uiStateAndEvents.state.uiState.accountFrom
                        } else {
                            null
                        },
                        accountTo = if (accountToId.isNotNull()) {
                            uiStateAndEvents.state.uiState.accountTo
                        } else {
                            null
                        },
                        transaction = Transaction(
                            amount = amount,
                            categoryId = categoryId,
                            originalTransactionId = addTransactionScreenInitialData?.originalTransactionData?.transaction?.id,
                            accountFromId = accountFromId,
                            accountToId = accountToId,
                            description = "",
                            // TODO(Abhi): Description
                            // description = uiStateAndEvents.state.description.text,
                            title = title,
                            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                            transactionTimestamp = transactionTimestamp,
                            transactionForId = transactionForId,
                            transactionType = uiStateAndEvents.state.selectedTransactionType,
                        ),
                    )
                }

                is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                    uiStateAndEvents.events.clearAmount()
                }

                is AddTransactionScreenUIEvent.OnClearDescriptionButtonClick -> {
                    // TODO(Abhi): Description
                }

                is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                    uiStateAndEvents.events.clearTitle()
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
                    uiStateAndEvents.events.setAccountFrom(uiEvent.updatedAccountFrom)
                }

                is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                    uiStateAndEvents.events.setAccountTo(uiEvent.updatedAccountTo)
                }

                is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                    uiStateAndEvents.events.setAmount(uiEvent.updatedAmount)
                }

                is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                    uiStateAndEvents.events.setCategory(uiEvent.updatedCategory)
                    viewModel.setCategory(uiEvent.updatedCategory)
                }

                is AddTransactionScreenUIEvent.OnDescriptionUpdated -> {
                    // TODO(Abhi): Description
                }

                is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                    uiStateAndEvents.events.setSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
                }

                is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    uiStateAndEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
                }

                is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionTimePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                    uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
                    viewModel.setTitle(uiEvent.updatedTitle.text)
                }

                is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                    uiStateAndEvents.events.setTransactionDate(uiEvent.updatedTransactionDate)
                    uiStateAndEvents.events.setIsTransactionDatePickerDialogVisible(false)
                }

                is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                    uiStateAndEvents.events.setTransactionTime(uiEvent.updatedTransactionTime)
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

    LaunchedEffect(
        key1 = addTransactionScreenInitialData,
    ) {
        addTransactionScreenInitialData?.let { addTransactionScreenData ->
            if (addTransactionScreenData.originalTransactionData != null) {
                uiStateAndEvents.events.setCategory(addTransactionScreenData.originalTransactionData.category)
                viewModel.setCategory(addTransactionScreenData.originalTransactionData.category)
                uiStateAndEvents.events.setAccountFrom(addTransactionScreenData.originalTransactionData.accountFrom)
                uiStateAndEvents.events.setAccountTo(addTransactionScreenData.originalTransactionData.accountTo)
                uiStateAndEvents.events.setSelectedTransactionTypeIndex(
                    // TODO(Abhi): Move this logic outside
                    addTransactionScreenData.validTransactionTypesForNewTransaction.indexOf(
                        element = TransactionType.REFUND,
                    )
                )
                uiStateAndEvents.events.setSelectedTransactionForIndex(
                    addTransactionScreenData.transactionForValues.indexOf(
                        element = addTransactionScreenData.transactionForValues.firstOrNull {
                            it.id == addTransactionScreenData.originalTransactionData.transaction.id
                        },
                    )
                )
            } else {
                uiStateAndEvents.events.setCategory(addTransactionScreenData.defaultExpenseCategory)
                viewModel.setCategory(addTransactionScreenData.defaultExpenseCategory)
                uiStateAndEvents.events.setAccountFrom(addTransactionScreenData.defaultAccount)
                uiStateAndEvents.events.setAccountTo(addTransactionScreenData.defaultAccount)
                uiStateAndEvents.events.setSelectedTransactionTypeIndex(
                    // TODO(Abhi): Move this logic outside
                    addTransactionScreenData.validTransactionTypesForNewTransaction.indexOf(
                        element = TransactionType.EXPENSE,
                    )
                )
            }
        }
    }

    AddTransactionScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
