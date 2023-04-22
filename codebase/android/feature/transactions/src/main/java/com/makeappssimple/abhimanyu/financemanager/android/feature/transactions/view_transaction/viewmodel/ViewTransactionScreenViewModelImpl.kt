package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class ViewTransactionScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
) : ViewTransactionScreenViewModel, ViewModel() {
    // Navigation parameters
    private var originalTransactionId: Int? =
        savedStateHandle.get<String>(NavArgs.TRANSACTION_ID)?.toIntOrNull()

    // Transaction data
    private var _transactionData: MutableStateFlow<TransactionData?> = MutableStateFlow(
        value = null,
    )
    override val transactionData: StateFlow<TransactionData?> = _transactionData

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
        originalTransactionId?.let { id ->
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
