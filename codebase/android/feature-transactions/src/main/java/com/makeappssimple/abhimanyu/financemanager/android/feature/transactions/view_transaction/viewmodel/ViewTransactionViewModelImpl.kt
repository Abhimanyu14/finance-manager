package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class ViewTransactionViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
) : ViewTransactionViewModel, ViewModel() {
    private var transactionId: Int? = null

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
        getNavigationArguments(
            savedStateHandle = savedStateHandle,
        )
        updateTransactionData()
    }

    private fun getNavigationArguments(
        savedStateHandle: SavedStateHandle,
    ) {
        savedStateHandle.get<String>(NavArgs.TRANSACTION_ID)?.let {
            transactionId = it.toIntOrNull()
        }
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
        transactionId?.let { id ->
            viewModelScope.launch(
                context = dispatcherProvider.io,
            ) {
                launch {
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
    }

    private fun updateOriginalTransactionData(
        transactionId: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                getTransactionDataUseCase(
                    id = transactionId,
                )?.let {
                    _originalTransactionData.value = it
                }
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
