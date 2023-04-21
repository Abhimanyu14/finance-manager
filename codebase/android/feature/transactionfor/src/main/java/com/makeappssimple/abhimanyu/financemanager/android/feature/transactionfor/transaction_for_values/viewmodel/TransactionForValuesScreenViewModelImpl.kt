package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class TransactionForValuesScreenViewModelImpl @Inject constructor(
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    override val navigationManager: NavigationManager,
    private val checkIfTransactionForIsUsedInTransactionsUseCase: CheckIfTransactionForIsUsedInTransactionsUseCase,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : TransactionForValuesScreenViewModel, ViewModel() {
    override val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    override val transactionForValuesIsUsedInTransactions: Flow<List<Boolean>> =
        transactionForValues
            .map {
                it.map { transactionFor ->
                    checkIfTransactionForIsUsedInTransactionsUseCase(
                        transactionForId = transactionFor.id,
                    )
                }
            }

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
