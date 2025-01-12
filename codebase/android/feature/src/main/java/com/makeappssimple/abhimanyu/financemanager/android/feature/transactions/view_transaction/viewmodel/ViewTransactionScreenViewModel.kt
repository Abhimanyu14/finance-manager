package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.ViewTransactionScreenArgs
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
public class ViewTransactionScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeKit: DateTimeKit,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    @VisibleForTesting internal val navigationKit: NavigationKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), ViewTransactionScreenUIStateDelegate by ViewTransactionScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    deleteTransactionUseCase = deleteTransactionUseCase,
    navigationKit = navigationKit,
) {
    // region screen args
    private val screenArgs = ViewTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var currentTransactionListItemData: TransactionListItemData? = null
    private var originalTransactionListItemData: TransactionListItemData? = null
    private var refundTransactionsListItemData: ImmutableList<TransactionListItemData> =
        persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<ViewTransactionScreenUIState> =
        MutableStateFlow(
            value = ViewTransactionScreenUIState(),
        )
    internal val uiStateEvents: ViewTransactionScreenUIStateEvents =
        ViewTransactionScreenUIStateEvents(
            deleteTransaction = ::deleteTransaction,
            navigateUp = ::navigateUp,
            navigateToEditTransactionScreen = ::navigateToEditTransactionScreen,
            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
            onRefundButtonClick = ::onRefundButtonClick,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            setScreenBottomSheetType = ::updateScreenBottomSheetType,
            setTransactionIdToDelete = {
                transactionIdToDelete = it
            },
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                getCurrentTransactionData()
            }
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
            dateTimeKit = dateTimeKit,
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

                uiState.update {
                    ViewTransactionScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                        isLoading = isLoading,
                        refundTransactionsListItemData = refundTransactionsListItemData.orEmpty(),
                        originalTransactionListItemData = originalTransactionListItemData,
                        transactionListItemData = currentTransactionListItemData,
                        screenBottomSheetType = screenBottomSheetType,
                    )
                }
            }
        }
    }
    // endregion
}
