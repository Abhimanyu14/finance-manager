package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class TransactionsScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val navigator: Navigator,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : TransactionsScreenUIStateDelegate {
    // region initial data
    override val allTransactionData: MutableStateFlow<ImmutableList<TransactionData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    override val selectedTransactionIndices: MutableStateFlow<ImmutableList<Int>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    override val transactionDetailsListItemViewData: MutableStateFlow<Map<String, ImmutableList<TransactionListItemData>>> =
        MutableStateFlow(
            value = mutableMapOf(),
        )
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val isInSelectionMode: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    override val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    override val selectedSortOption: MutableStateFlow<SortOption> = MutableStateFlow(
        value = SortOption.LATEST_FIRST,
    )
    override val screenBottomSheetType: MutableStateFlow<TransactionsScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionsScreenBottomSheetType.None,
        )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    override fun addToSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactionIndices.update {
            it.toMutableList().apply {
                add(transactionId)
            }.toImmutableList()
        }
    }

    override fun clearSelectedTransactions() {
        selectedTransactionIndices.update {
            persistentListOf()
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun removeFromSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactionIndices.update {
            it.toMutableList().apply {
                remove(transactionId)
            }.toImmutableList()
        }
    }

    override fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun selectAllTransactions() {
        selectedTransactionIndices.update {
            transactionDetailsListItemViewData.value.values.flatMap {
                it.map { transactionListItemData ->
                    transactionListItemData.transactionId
                }
            }.toImmutableList()
        }
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedTransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedTransactionsScreenBottomSheetType
        }
    }

    override fun setIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
    ) {
        isInSelectionMode.update {
            updatedIsInSelectionMode
        }
    }

    override fun setSearchText(
        updatedSearchText: String,
    ) {
        searchText.update {
            updatedSearchText
        }
    }

    override fun setSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    override fun setSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ) {
        selectedSortOption.update {
            updatedSelectedSortOption
        }
    }

    override fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    ) {
        val selectedTransactions: ImmutableList<Int> = selectedTransactionIndices.value
        coroutineScope.launch {
            val updatedTransactions = allTransactionData.value.map { transactionData ->
                transactionData.transaction
            }.filter {
                it.transactionType == TransactionType.EXPENSE &&
                        selectedTransactions.contains(it.id)
            }.map {
                it
                    .copy(
                        transactionForId = transactionForId,
                    )
            }
            updateTransactionsUseCase(
                transactions = updatedTransactions.toTypedArray(),
            )
        }
    }
    // endregion
}
