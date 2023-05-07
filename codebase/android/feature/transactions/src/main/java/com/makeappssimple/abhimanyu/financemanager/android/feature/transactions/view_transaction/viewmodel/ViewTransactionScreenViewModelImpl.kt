package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.ViewTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class ViewTransactionScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val dateTimeUtil: DateTimeUtil,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
) : ViewTransactionScreenViewModel, ViewModel() {
    private var viewTransactionScreenArgs: ViewTransactionScreenArgs = ViewTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // Transaction data
    private var _transactionData: MutableStateFlow<TransactionData?> = MutableStateFlow(
        value = null,
    )
    override val transactionData: StateFlow<TransactionData?> = _transactionData

    // Transaction data
    private var _transactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )
    override val transactionListItemData: StateFlow<TransactionListItemData?> =
        _transactionListItemData

    // Original transaction data
    private var _originalTransactionData: MutableStateFlow<TransactionData?> = MutableStateFlow(
        value = null,
    )
    override val originalTransactionData: StateFlow<TransactionData?> = _originalTransactionData

    // Original transaction data
    private var _refundTransactionData: MutableStateFlow<List<TransactionData>> = MutableStateFlow(
        value = emptyList(),
    )
    override val refundTransactionData: StateFlow<List<TransactionData>> = _refundTransactionData

    init {
        updateTransactionData()
    }

    override fun deleteTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionAndRevertOtherDataUseCase(
                id = id,
            )
        }
    }

    override fun updateTransactionData() {
        viewTransactionScreenArgs.originalTransactionId?.let { id ->
            viewModelScope.launch(
                context = dispatcherProvider.io,
            ) {
                getTransactionDataUseCase(
                    id = id,
                )?.let {
                    _transactionData.value = it

                    it.transaction.originalTransactionId?.let { transactionId ->
                        updateOriginalTransactionData(
                            transactionId = transactionId,
                        )
                    }

                    it.transaction.refundTransactionIds?.let { ids ->
                        updateRefundTransactionData(
                            ids = ids,
                        )
                    }


                    val transaction = it.transaction
                    val isDeleteButtonEnabled = transaction.refundTransactionIds?.run {
                        this.isEmpty()
                    } ?: true
                    val isEditButtonVisible =
                        transaction.transactionType != TransactionType.ADJUSTMENT
                    val isRefundButtonVisible =
                        transaction.transactionType == TransactionType.EXPENSE

                    val amountColor: MyColor = transaction.getAmountTextColor()
                    val amountText: String = if (
                        transaction.transactionType == TransactionType.INCOME ||
                        transaction.transactionType == TransactionType.EXPENSE ||
                        transaction.transactionType == TransactionType.ADJUSTMENT ||
                        transaction.transactionType == TransactionType.REFUND
                    ) {
                        it.transaction.amount.toSignedString(
                            isPositive = it.sourceTo.isNotNull(),
                            isNegative = it.sourceFrom.isNotNull(),
                        )
                    } else {
                        transaction.amount.toString()
                    }
                    val dateAndTimeText: String = dateTimeUtil.getReadableDateAndTime(
                        timestamp = transaction.transactionTimestamp,
                    )
                    val emoji: String = when (transaction.transactionType) {
                        TransactionType.TRANSFER -> {
                            EmojiConstants.LEFT_RIGHT_ARROW
                        }

                        TransactionType.ADJUSTMENT -> {
                            EmojiConstants.EXPRESSIONLESS_FACE
                        }

                        else -> {
                            it.category?.emoji.orEmpty()
                        }
                    }
                    val sourceFromName = it.sourceFrom?.name
                    val sourceToName = it.sourceTo?.name
                    val title: String = transaction.title
                    val transactionForText: String = it.transactionFor.titleToDisplay


                    _transactionListItemData.value = TransactionListItemData(
                        isDeleteButtonEnabled = isDeleteButtonEnabled,
                        isDeleteButtonVisible = true,
                        isEditButtonVisible = isEditButtonVisible,
                        isExpanded = true,
                        isRefundButtonVisible = isRefundButtonVisible,
                        transactionId = transaction.id,
                        amountColor = amountColor,
                        amountText = amountText,
                        dateAndTimeText = dateAndTimeText,
                        emoji = emoji,
                        sourceFromName = sourceFromName,
                        sourceToName = sourceToName,
                        title = title,
                        transactionForText = transactionForText,
                        onClick = null,
                    )
                }
            }
        }
    }

    private fun updateOriginalTransactionData(
        transactionId: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            getTransactionDataUseCase(
                id = transactionId,
            )?.let {
                _originalTransactionData.value = it
            }
        }
    }

    private fun updateRefundTransactionData(
        ids: List<Int>,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            // TODO-Abhi: Optimize later
            _refundTransactionData.value = emptyList()
            ids.forEach {
                launch {
                    getTransactionDataUseCase(
                        id = it,
                    )?.let {
                        _refundTransactionData.value
                        _originalTransactionData.value = it
                    }
                }
            }
        }
    }
}
