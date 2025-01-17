package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.mapIndexed
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TransactionForValuesScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val checkIfTransactionForValuesAreUsedInTransactionsUseCase: CheckIfTransactionForValuesAreUsedInTransactionsUseCase,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), TransactionForValuesScreenUIStateDelegate by TransactionForValuesScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    deleteTransactionForUseCase = deleteTransactionForUseCase,
    navigationKit = navigationKit,
) {
    // region initial data
    private var transactionForListItemDataList: ImmutableList<TransactionForListItemData> =
        persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<TransactionForValuesScreenUIState> =
        MutableStateFlow(
            value = TransactionForValuesScreenUIState(),
        )
    internal val uiStateEvents: TransactionForValuesScreenUIStateEvents =
        TransactionForValuesScreenUIStateEvents(
            deleteTransactionFor = ::deleteTransactionFor,
            navigateToAddTransactionForScreen = ::navigateToAddTransactionForScreen,
            navigateToEditTransactionForScreen = ::navigateToEditTransactionForScreen,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            setScreenBottomSheetType = ::updateScreenBottomSheetType,
            setTransactionForIdToDelete = ::updateTransactionForIdToDelete,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {}

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
                uiState.update {
                    TransactionForValuesScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                        isLoading = isLoading,
                        transactionForIdToDelete = transactionForIdToDelete,
                        transactionForListItemDataList = transactionForListItemDataList,
                        screenBottomSheetType = screenBottomSheetType,
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
}
