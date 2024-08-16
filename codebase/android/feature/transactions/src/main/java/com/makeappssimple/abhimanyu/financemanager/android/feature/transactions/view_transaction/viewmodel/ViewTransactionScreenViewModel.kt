package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.ViewTransactionScreenArgs
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents
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
) : ScreenViewModel() {
    // region screen args
    private val screenArgs = ViewTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var transactionIdToDelete: Int? = null
    private var currentTransactionListItemData: TransactionListItemData? = null
    private var originalTransactionListItemData: TransactionListItemData? = null
    private var refundTransactionsListItemData: ImmutableList<TransactionListItemData> =
        persistentListOf()
    // endregion

    // region UI state
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )

    private val screenBottomSheetType: MutableStateFlow<ViewTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = ViewTransactionScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<ViewTransactionScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = ViewTransactionScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            startLoading()
            getCurrentTransactionData()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region getCurrentTransactionData
    private suspend fun getCurrentTransactionData() {
        val currentTransactionId = screenArgs.transactionId ?: return
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
        refundTransactionsListItemData = transactionIds.mapNotNull { transactionId ->
            getTransactionDataUseCase(
                id = transactionId,
            )?.let { transactionData ->
                getTransactionListItemData(
                    transactionData = transactionData,
                ) // TODO(Abhi): Show error message
            }
        }.toImmutableList()
    }

    private fun getTransactionListItemData(
        transactionData: TransactionData,
    ): TransactionListItemData {
        val transaction = transactionData.transaction
        return transactionData.toTransactionListItemData(
            dateTimeUtil = dateTimeUtil,
        )
            .copy(
                isDeleteButtonEnabled = transaction.refundTransactionIds.isNullOrEmpty(),
                isDeleteButtonVisible = true,
                isEditButtonVisible = transaction.transactionType != TransactionType.ADJUSTMENT,
                isExpanded = true,
                isRefundButtonVisible = transaction.transactionType == TransactionType.EXPENSE,
            )
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
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
        val id = transactionIdToDelete ?: return
        viewModelScope.launch {
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
