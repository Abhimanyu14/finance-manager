package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.mapIndexed
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class TransactionForValuesScreenViewModel @Inject constructor(
    private val getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val checkIfTransactionForValuesAreUsedInTransactionsUseCase: CheckIfTransactionForValuesAreUsedInTransactionsUseCase,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private var transactionForListItemDataList: ImmutableList<TransactionForListItemData> =
        persistentListOf()
    // endregion

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

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<TransactionForValuesScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = TransactionForValuesScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTransactionForListItemDataList()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                transactionForIdToDelete,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        transactionForIdToDelete,
                    ),
                ->
                uiStateAndStateEvents.update {
                    TransactionForValuesScreenUIStateAndStateEvents(
                        state = TransactionForValuesScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                            isLoading = isLoading,
                            transactionForIdToDelete = transactionForIdToDelete,
                            transactionForListItemDataList = transactionForListItemDataList,
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
    // endregion

    // region observeForTransactionForListItemDataList
    private fun observeForTransactionForListItemDataList() {
        viewModelScope.launch {
            getAllTransactionForValuesFlowUseCase().collectLatest { updatedAllTransactionForValues ->
                startLoading()
                val transactionForValuesIsUsedInTransactions =
                    checkIfTransactionForValuesAreUsedInTransactionsUseCase(
                        transactionForValues = updatedAllTransactionForValues,
                    )
                transactionForListItemDataList = updatedAllTransactionForValues
                    .mapIndexed { index: Int, transactionFor: TransactionFor ->
                        TransactionForListItemData(
                            transactionForId = transactionFor.id,
                            isMoreOptionsIconButtonVisible = true,
                            isDeleteBottomSheetMenuItemVisible = !isDefaultTransactionFor(
                                transactionFor = transactionFor.title,
                            ) && transactionForValuesIsUsedInTransactions.getOrNull(
                                index = index,
                            )?.not().orFalse(),
                            title = transactionFor.title.capitalizeWords(),
                        )
                    }
                completeLoading()
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
    private fun deleteTransactionFor(
        id: Int,
    ) {
        viewModelScope.launch {
            val isTransactionForDeleted = deleteTransactionForUseCase(
                id = id,
            )
            if (!isTransactionForDeleted) {
                // TODO(Abhi): Show error
            }
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
