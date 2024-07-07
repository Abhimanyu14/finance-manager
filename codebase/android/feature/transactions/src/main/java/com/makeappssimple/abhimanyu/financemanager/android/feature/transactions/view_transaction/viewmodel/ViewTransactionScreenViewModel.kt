package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
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

    // region initial data
    private var transactionIdToDelete: Int? = null
    private var currentTransactionListItemData: TransactionListItemData? = null
    private var originalTransactionListItemData: TransactionListItemData? = null
    private var refundTransactionsListItemData: MutableList<TransactionListItemData> =
        mutableListOf()
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )

    private val screenBottomSheetType: MutableStateFlow<ViewTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = ViewTransactionScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<ViewTransactionScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = ViewTransactionScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getCurrentTransactionData()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    // region getCurrentTransactionData
    private suspend fun getCurrentTransactionData() {
        val currentTransactionId = screenArgs.currentTransactionId ?: return
        val transactionData = getTransactionDataUseCase(
            id = currentTransactionId,
        ) ?: return // TODO(Abhi): Show error message
        currentTransactionListItemData = getTransactionListItemData(
            transactionData = transactionData,
        )
        transactionData.transaction.originalTransactionId?.let { transactionId ->
            getOriginalTransactionData(
                transactionId = transactionId,
            )
        }
        transactionData.transaction.refundTransactionIds?.let { transactionIds ->
            getRefundTransactionsData(
                transactionIds = transactionIds.toImmutableList(),
            )
        }
    }

    private suspend fun getOriginalTransactionData(
        transactionId: Int,
    ) {
        val transactionData = getTransactionDataUseCase(
            id = transactionId,
        ) ?: return // TODO(Abhi): Show error message
        originalTransactionListItemData = getTransactionListItemData(
            transactionData = transactionData,
        )
    }

    private suspend fun getRefundTransactionsData(
        transactionIds: ImmutableList<Int>,
    ) {
        val transactionsListItemData = transactionIds.mapNotNull { transactionId ->
            getTransactionDataUseCase(
                id = transactionId,
            )?.let { transactionData ->
                getTransactionListItemData(
                    transactionData = transactionData,
                ) // TODO(Abhi): Show error message
            }
        }.toImmutableList()
        refundTransactionsListItemData.clear()
        refundTransactionsListItemData.addAll(transactionsListItemData)
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
    // endregion

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                    ),
                ->

                uiStateAndStateEvents.update {
                    ViewTransactionScreenUIStateAndStateEvents(
                        state = ViewTransactionScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                            isLoading = isLoading,
                            refundTransactionsListItemData = refundTransactionsListItemData.orEmpty(),
                            originalTransactionListItemData = originalTransactionListItemData,
                            transactionListItemData = currentTransactionListItemData,
                            screenBottomSheetType = screenBottomSheetType,
                        ),
                        events = ViewTransactionScreenUIStateEvents(
                            deleteTransaction = ::deleteTransaction,
                            navigateUp = ::navigateUp,
                            navigateToEditTransactionScreen = ::navigateToEditTransactionScreen,
                            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
                            onRefundButtonClick = ::onRefundButtonClick,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setTransactionIdToDelete = ::setTransactionIdToDelete,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun deleteTransaction() {
        viewModelScope.launch {
            val id = transactionIdToDelete ?: return@launch
            startLoading()
            val isTransactionDeleted = deleteTransactionUseCase(
                id = id,
            )
            setTransactionIdToDelete(null)
            resetScreenBottomSheetType()
            if (isTransactionDeleted) {
                // TODO(Abhi): Show success message
                // TODO(Abhi): Change to navigate up only if the current transaction is deleted
                navigateUp()
            } else {
                // TODO(Abhi): Show error message
            }
            completeLoading()
        }
    }

    private fun onRefundButtonClick(
        transactionId: Int,
    ) {
        navigator.navigateToAddTransactionScreen(
            transactionId = transactionId,
        )
    }

    private fun navigateToEditTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToEditTransactionScreen(
            transactionId = transactionId,
        )
    }

    private fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedViewTransactionScreenBottomSheetType = ViewTransactionScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedViewTransactionScreenBottomSheetType
        }
    }

    private fun setTransactionIdToDelete(
        updatedTransactionIdToDelete: Int?,
    ) {
        transactionIdToDelete = updatedTransactionIdToDelete
    }
    // endregion
}
