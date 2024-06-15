package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quintuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class TransactionForValuesScreenViewModel @Inject constructor(
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val checkIfTransactionForIsUsedInTransactionsUseCase: CheckIfTransactionForIsUsedInTransactionsUseCase,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val transactionForValues: StateFlow<ImmutableList<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase()
            .defaultListStateIn(
                scope = viewModelScope,
            )
    private val transactionForValuesIsUsedInTransactions: StateFlow<ImmutableList<Boolean>> =
        transactionForValues.map {
            it.map { transactionFor ->
                checkIfTransactionForIsUsedInTransactionsUseCase(
                    transactionForId = transactionFor.id,
                )
            }
        }.defaultListStateIn(
            scope = viewModelScope,
        )

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val transactionForIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private val screenBottomSheetType: MutableStateFlow<TransactionForValuesScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionForValuesScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<TransactionForValuesScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = TransactionForValuesScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
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
                transactionForIdToDelete,
                transactionForValuesIsUsedInTransactions,
                transactionForValues,
            ) {
                    isLoading,
                    screenBottomSheetType,
                    transactionForIdToDelete,
                    transactionForValuesIsUsedInTransactions,
                    transactionForValues,
                ->
                Quintuple(
                    isLoading,
                    screenBottomSheetType,
                    transactionForIdToDelete,
                    transactionForValuesIsUsedInTransactions,
                    transactionForValues,
                )
            }.collectLatest {
                    (
                        isLoading,
                        screenBottomSheetType,
                        transactionForIdToDelete,
                        transactionForValuesIsUsedInTransactions,
                        transactionForValues,
                    ),
                ->
                uiStateAndStateEvents.update {
                    TransactionForValuesScreenUIStateAndStateEvents(
                        state = TransactionForValuesScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                            transactionForIdToDelete = transactionForIdToDelete,
                            transactionForValuesIsUsedInTransactions = transactionForValuesIsUsedInTransactions,
                            transactionForValues = transactionForValues,
                            screenBottomSheetType = screenBottomSheetType,
                        ),
                        events = TransactionForValuesScreenUIStateEvents(
                            deleteTransactionFor = ::deleteTransactionFor,
                            navigateToAddTransactionForScreen = ::navigateToAddTransactionForScreen,
                            navigateToEditTransactionForScreen = ::navigateToEditTransactionForScreen,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setTransactionForIdToDelete = ::setTransactionForIdToDelete,
                        ),
                    )
                }
            }
        }
    }

    // region state events
    private fun deleteTransactionFor(
        id: Int,
    ) {
        viewModelScope.launch {
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

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedTransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedTransactionForValuesScreenBottomSheetType
        }
    }

    private fun setTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
    ) {
        transactionForIdToDelete.update {
            updatedTransactionForIdToDelete
        }
    }
    // endregion
}
