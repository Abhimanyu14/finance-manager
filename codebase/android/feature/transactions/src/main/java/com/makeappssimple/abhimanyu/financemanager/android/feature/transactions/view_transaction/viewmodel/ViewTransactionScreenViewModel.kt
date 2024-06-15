package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Sextuple
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private var originalTransactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )
    private var refundTransactionListItemData: MutableStateFlow<ImmutableList<TransactionListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private var currentTransactionListItemData: MutableStateFlow<TransactionListItemData?> =
        MutableStateFlow(
            value = null,
        )

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val transactionIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
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
        getCurrentTransactionData()
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combine(
                isLoading,
                screenBottomSheetType,
                transactionIdToDelete,
                refundTransactionListItemData,
                originalTransactionListItemData,
                currentTransactionListItemData,
            ) {
                    isLoading,
                    screenBottomSheetType,
                    transactionIdToDelete,
                    refundTransactionListItemData,
                    originalTransactionListItemData,
                    currentTransactionListItemData,
                ->
                Sextuple(
                    isLoading,
                    screenBottomSheetType,
                    transactionIdToDelete,
                    refundTransactionListItemData,
                    originalTransactionListItemData,
                    currentTransactionListItemData,
                )
            }.collectLatest {
                    (
                        isLoading,
                        screenBottomSheetType,
                        transactionIdToDelete,
                        refundTransactionListItemData,
                        originalTransactionListItemData,
                        currentTransactionListItemData,
                    ),
                ->

                uiStateAndStateEvents.update {
                    ViewTransactionScreenUIStateAndStateEvents(
                        state = ViewTransactionScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                            isLoading = isLoading,
                            transactionIdToDelete = transactionIdToDelete,
                            refundTransactionListItemData = refundTransactionListItemData,
                            originalTransactionListItemData = originalTransactionListItemData,
                            transactionListItemData = currentTransactionListItemData,
                            screenBottomSheetType = screenBottomSheetType,
                        ),
                        events = ViewTransactionScreenUIStateEvents(
                            deleteTransaction = ::deleteTransaction,
                            navigateUp = ::navigateUp,
                            navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
                            navigateToEditTransactionScreen = ::navigateToEditTransactionScreen,
                            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setTransactionIdToDelete = ::setTransactionIdToDelete,
                        ),
                    )
                }
            }
        }
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
                            transactionIds = transactionIds.toImmutableList(),
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
        transactionIds: ImmutableList<Int>,
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

    // region state events
    private fun deleteTransaction(
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

    private fun navigateToAddTransactionScreen(
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
        transactionIdToDelete.update {
            updatedTransactionIdToDelete
        }
    }
    // endregion
}
