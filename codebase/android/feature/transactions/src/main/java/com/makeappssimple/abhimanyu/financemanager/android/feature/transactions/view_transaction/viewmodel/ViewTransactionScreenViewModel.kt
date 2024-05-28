package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.ViewTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
public class ViewTransactionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeUtil: DateTimeUtil,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    @VisibleForTesting internal val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private var screenArgs = ViewTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    public var originalTransactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )
    public var refundTransactionListItemData: MutableStateFlow<ImmutableList<TransactionListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    public var currentTransactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )

    public fun initViewModel() {
        fetchData()
    }

    public fun deleteTransaction(
        transactionId: Int,
    ) {
        viewModelScope.launch {
            val isTransactionDeleted = deleteTransactionUseCase(
                id = transactionId,
            )
            if (isTransactionDeleted) {
                navigateUp()
            } else {
                // TODO(Abhi): Show error message
            }
        }
    }

    public fun navigateToAddTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToAddTransactionScreen(
            transactionId = transactionId,
        )
    }

    public fun navigateToEditTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToEditTransactionScreen(
            transactionId = transactionId,
        )
    }

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    private fun fetchData() {
        getCurrentTransactionData()
    }

    private fun getCurrentTransactionData() {
        screenArgs.currentTransactionId?.let { id ->
            viewModelScope.launch {
                getTransactionDataUseCase(
                    id = id,
                )?.let { transactionData ->
                    currentTransactionListItemData.update {
                        getTransactionListItemData(
                            transactionData = transactionData,
                        )
                    }
                    transactionData.transaction.originalTransactionId?.let { transactionId ->
                        getOriginalTransactionData(
                            transactionId = transactionId,
                        )
                    }
                    transactionData.transaction.refundTransactionIds?.let { transactionIds ->
                        getRefundTransactionsData(
                            transactionIds = transactionIds,
                        )
                    }
                }
            }
        }
    }

    private suspend fun getOriginalTransactionData(
        transactionId: Int,
    ) {
        getTransactionDataUseCase(
            id = transactionId,
        )?.let { transactionData ->
            originalTransactionListItemData.value = getTransactionListItemData(
                transactionData = transactionData,
            )
        }
    }

    private suspend fun getRefundTransactionsData(
        transactionIds: List<Int>,
    ) {
        val transactionListItemData = mutableListOf<TransactionListItemData>()
        transactionIds.forEach { transactionId ->
            getTransactionDataUseCase(
                id = transactionId,
            )?.let { transactionData ->
                transactionListItemData.add(
                    getTransactionListItemData(
                        transactionData = transactionData,
                    )
                )
            }
        }
        refundTransactionListItemData.update {
            transactionListItemData.toImmutableList()
        }
    }

    private fun getTransactionListItemData(
        transactionData: TransactionData,
    ): TransactionListItemData {
        val transaction = transactionData.transaction
        val amountText: String = when (transaction.transactionType) {
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.ADJUSTMENT,
            TransactionType.REFUND,
            -> {
                transactionData.transaction.amount.toSignedString(
                    isPositive = transactionData.accountTo.isNotNull(),
                    isNegative = transactionData.accountFrom.isNotNull(),
                )
            }

            else -> {
                transaction.amount.toString()
            }
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
                transactionData.category?.emoji.orEmpty()
            }
        }

        return TransactionListItemData(
            isDeleteButtonEnabled = transaction.refundTransactionIds.isNullOrEmpty(),
            isDeleteButtonVisible = true,
            isEditButtonVisible = transaction.transactionType != TransactionType.ADJUSTMENT,
            isExpanded = true,
            isRefundButtonVisible = transaction.transactionType == TransactionType.EXPENSE,
            transactionId = transaction.id,
            amountColor = transaction.getAmountTextColor(),
            amountText = amountText,
            dateAndTimeText = dateAndTimeText,
            emoji = emoji,
            accountFromName = transactionData.accountFrom?.name,
            accountToName = transactionData.accountTo?.name,
            title = transaction.title,
            transactionForText = transactionData.transactionFor.titleToDisplay,
        )
    }
}
