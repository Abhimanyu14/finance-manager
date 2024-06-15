package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenInitialData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel
import java.time.LocalDateTime

internal class AddTransactionScreenEventHandler(
    private val uiState: AddTransactionScreenUIState,
    private val uiStateEvents: AddTransactionScreenUIStateEvents,
    private val viewModel: AddTransactionScreenViewModel,
    private val addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    private val dateTimeUtil: DateTimeUtil = DateTimeUtilImpl(),
) {
    fun handleUIEvent(
        uiEvent: AddTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnCtaButtonClick -> {
                val amountValue = uiState.uiState.amount.text.toLongOrZero()
                val amount = Amount(
                    value = amountValue,
                )
                val categoryId = when (uiState.selectedTransactionType) {
                    TransactionType.INCOME -> {
                        uiState.uiState.category?.id
                    }

                    TransactionType.EXPENSE -> {
                        uiState.uiState.category?.id
                    }

                    TransactionType.TRANSFER -> {
                        null
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiState.uiState.category?.id
                    }

                    TransactionType.REFUND -> {
                        uiState.uiState.category?.id
                    }
                }
                val accountFromId = when (uiState.selectedTransactionType) {
                    TransactionType.INCOME -> {
                        null
                    }

                    TransactionType.EXPENSE -> {
                        uiState.uiState.accountFrom?.id
                    }

                    TransactionType.TRANSFER -> {
                        uiState.uiState.accountFrom?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiState.uiState.accountFrom?.id
                    }

                    TransactionType.REFUND -> {
                        null
                    }
                }
                val accountToId = when (uiState.selectedTransactionType) {
                    TransactionType.INCOME -> {
                        uiState.uiState.accountTo?.id
                    }

                    TransactionType.EXPENSE -> {
                        null
                    }

                    TransactionType.TRANSFER -> {
                        uiState.uiState.accountTo?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        null
                    }

                    TransactionType.REFUND -> {
                        uiState.uiState.accountTo?.id
                    }
                }
                val title = when (uiState.selectedTransactionType) {
                    TransactionType.TRANSFER -> {
                        TransactionType.TRANSFER.title
                    }

                    TransactionType.REFUND -> {
                        TransactionType.REFUND.title
                    }

                    else -> {
                        uiState.uiState.title.text.capitalizeWords()
                    }
                }
                val transactionForId: Int =
                    when (uiState.selectedTransactionType) {
                        TransactionType.INCOME -> {
                            1
                        }

                        TransactionType.EXPENSE -> {
                            uiState.uiState.selectedTransactionForIndex
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
                        uiState.uiState.transactionDate,
                        uiState.uiState.transactionTime
                    )
                    .toEpochMilli()

                viewModel.insertTransaction(
                    amountValue = amountValue,
                    accountFrom = if (accountFromId.isNotNull()) {
                        uiState.uiState.accountFrom
                    } else {
                        null
                    },
                    accountTo = if (accountToId.isNotNull()) {
                        uiState.uiState.accountTo
                    } else {
                        null
                    },
                    transaction = Transaction(
                        amount = amount,
                        categoryId = categoryId,
                        originalTransactionId = addTransactionScreenInitialData?.originalTransactionData?.transaction?.id,
                        accountFromId = accountFromId,
                        accountToId = accountToId,
                        title = title,
                        creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                        transactionTimestamp = transactionTimestamp,
                        transactionForId = transactionForId,
                        transactionType = uiState.selectedTransactionType,
                    ),
                )
            }

            is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateEvents.clearAmount()
            }

            is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is AddTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.setIsTransactionTimePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.setIsTransactionDatePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.setAccountFrom(uiEvent.updatedAccountFrom)
            }

            is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.setAccountTo(uiEvent.updatedAccountTo)
            }

            is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.setAmount(uiEvent.updatedAmount)
            }

            is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.setCategory(uiEvent.updatedCategory)
                viewModel.setCategory(uiEvent.updatedCategory)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.setSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.setIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.setIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.setTitle(uiEvent.updatedTitle)
                viewModel.setTitle(uiEvent.updatedTitle.text)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.setTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.setIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.setTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.setIsTransactionTimePickerDialogVisible(false)
            }
        }
    }
}
