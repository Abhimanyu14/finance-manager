package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.ViewTransactionScreenArgs
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    private var originalTransactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )
    private var refundTransactionListItemData: MutableStateFlow<List<TransactionListItemData>> =
        MutableStateFlow(
            value = emptyList(),
        )
    private var transactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )

    public val screenUIData: StateFlow<MyResult<ViewTransactionScreenUIData>?> = combine(
        originalTransactionListItemData,
        refundTransactionListItemData,
        transactionListItemData,
    ) {
            originalTransactionListItemData,
            refundTransactionListItemData,
            transactionListItemData,
        ->
        if (
            transactionListItemData.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = ViewTransactionScreenUIData(
                    originalTransactionListItemData = originalTransactionListItemData,
                    refundTransactionListItemData = refundTransactionListItemData,
                    transactionListItemData = transactionListItemData,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    public fun initViewModel() {
        getTransactionData()
    }

    public fun deleteTransaction(
        transactionId: Int,
    ) {
        viewModelScope.launch {
            deleteTransactionUseCase(
                id = transactionId,
            )
            navigateUp()
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

    private fun getTransactionData() {
        screenArgs.originalTransactionId?.let { id ->
            viewModelScope.launch {
                getTransactionDataUseCase(
                    id = id,
                )?.let { transactionData ->
                    transactionListItemData.value = getTransactionListItemData(
                        transactionData = transactionData,
                    )
                    transactionData.transaction.originalTransactionId?.let { transactionId ->
                        updateOriginalTransactionData(
                            transactionId = transactionId,
                        )
                    }
                    transactionData.transaction.refundTransactionIds?.let { ids ->
                        updateRefundTransactionData(
                            ids = ids,
                        )
                    }
                }
            }
        }
    }

    private fun getTransactionListItemData(
        transactionData: TransactionData,
    ): TransactionListItemData {
        val transaction = transactionData.transaction
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
            transactionData.transaction.amount.toSignedString(
                isPositive = transactionData.accountTo.isNotNull(),
                isNegative = transactionData.accountFrom.isNotNull(),
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
                transactionData.category?.emoji.orEmpty()
            }
        }
        val accountFromName = transactionData.accountFrom?.name
        val accountToName = transactionData.accountTo?.name
        val title: String = transaction.title
        val transactionForText: String = transactionData.transactionFor.titleToDisplay

        return TransactionListItemData(
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
            accountFromName = accountFromName,
            accountToName = accountToName,
            title = title,
            transactionForText = transactionForText,
        )
    }

    private fun updateOriginalTransactionData(
        transactionId: Int,
    ) {
        viewModelScope.launch {
            getTransactionDataUseCase(
                id = transactionId,
            )?.let {
                originalTransactionListItemData.value = getTransactionListItemData(
                    transactionData = it,
                )
            }
        }
    }

    private fun updateRefundTransactionData(
        ids: List<Int>,
    ) {
        viewModelScope.launch {
            refundTransactionListItemData.value = buildList {
                ids.map {
                    getTransactionDataUseCase(
                        id = it,
                    )?.let { transactionData ->
                        add(
                            getTransactionListItemData(
                                transactionData = transactionData,
                            )
                        )
                    }
                }
            }
        }
    }
}
