package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIEvent
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
    private val checkIfTransactionForIsUsedInTransactionsUseCase: CheckIfTransactionForIsUsedInTransactionsUseCase,
    private val closeableCoroutineScope: CloseableCoroutineScope,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val navigator: Navigator,
) : TransactionForValuesScreenViewModel, ViewModel(closeableCoroutineScope) {
    private val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
            scope = closeableCoroutineScope,
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
        if (
            transactionForValuesIsUsedInTransactions.isNull() ||
            transactionForValues.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = TransactionForValuesScreenUIData(
                    transactionForValuesIsUsedInTransactions = transactionForValuesIsUsedInTransactions,
                    transactionForValues = transactionForValues,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = closeableCoroutineScope,
    )

    override fun handleUIEvents(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.DeleteTransactionFor -> {
                deleteTransactionFor(
                    id = uiEvent.transactionForId,
                )
            }

            is TransactionForValuesScreenUIEvent.NavigateToAddTransactionForScreen -> {
                navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.NavigateToEditTransactionForScreen -> {
                navigateToEditTransactionForScreen(
                    transactionForId = uiEvent.transactionForId,
                )
            }

            is TransactionForValuesScreenUIEvent.NavigateUp -> {
                navigateUp()
            }
        }
    }

    private fun deleteTransactionFor(
        id: Int,
    ) {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionForUseCase(
                id = id,
            )
        }
    }

    private fun navigateToAddTransactionForScreen() {
        navigator.navigateToAddTransactionForScreen()
    }

    private fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
        navigator.navigateToEditTransactionForScreen(
            transactionForId = transactionForId,
        )
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }
}
