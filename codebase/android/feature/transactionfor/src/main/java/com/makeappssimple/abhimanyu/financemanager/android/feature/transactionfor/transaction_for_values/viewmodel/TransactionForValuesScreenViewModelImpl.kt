package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TransactionForValuesScreenViewModelImpl @Inject constructor(
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val checkIfTransactionForIsUsedInTransactionsUseCase: CheckIfTransactionForIsUsedInTransactionsUseCase,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : TransactionForValuesScreenViewModel, ViewModel() {
    private val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    private val transactionForValuesIsUsedInTransactions: Flow<List<Boolean>> =
        transactionForValues
            .map {
                it.map { transactionFor ->
                    checkIfTransactionForIsUsedInTransactionsUseCase(
                        transactionForId = transactionFor.id,
                    )
                }
            }

    override val screenUIData: StateFlow<MyResult<TransactionForValuesScreenUIData>?> = combine(
        transactionForValuesIsUsedInTransactions,
        transactionForValues,
    ) {
            transactionForValuesIsUsedInTransactions,
            transactionForValues,
        ->
        MyResult.Success(
            data = TransactionForValuesScreenUIData(
                transactionForValuesIsUsedInTransactions = transactionForValuesIsUsedInTransactions,
                transactionForValues = transactionForValues,
            ),
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun deleteTransactionFor(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionForUseCase(
                id = id,
            )
        }
    }
}
