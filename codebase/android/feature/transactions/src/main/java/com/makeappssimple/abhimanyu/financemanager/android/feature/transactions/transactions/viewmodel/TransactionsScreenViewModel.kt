package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
public class TransactionsScreenViewModel @Inject constructor(
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val navigator: Navigator,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : ScreenViewModel, ViewModel() {
    public val allTransactionData: StateFlow<ImmutableList<TransactionData>> =
        getAllTransactionDataFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    private val categoriesMap: Flow<Map<TransactionType, ImmutableList<Category>>> =
        allTransactionData.map {
            it.mapNotNull { transactionData ->
                transactionData.category
            }.groupBy { category ->
                category.transactionType
            }.mapValues { (_, categories) ->
                categories.distinct().toImmutableList()
            }.toMap()
        }

    public val expenseCategories: StateFlow<ImmutableList<Category>?> = categoriesMap.map {
        it[TransactionType.EXPENSE].orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    public val incomeCategories: StateFlow<ImmutableList<Category>?> = categoriesMap.map {
        it[TransactionType.INCOME].orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    public val investmentCategories: StateFlow<ImmutableList<Category>?> = categoriesMap.map {
        it[TransactionType.INVESTMENT].orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    public val accounts: StateFlow<ImmutableList<Account>?> = allTransactionData.map {
        it.flatMap { transactionData ->
            listOfNotNull(
                transactionData.accountFrom,
                transactionData.accountTo,
            )
        }.distinct().toImmutableList()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    public val transactionForValues: StateFlow<ImmutableList<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    public val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    public var oldestTransactionLocalDate: StateFlow<LocalDate?> = allTransactionData.map {
        dateTimeUtil.getLocalDate(
            timestamp = it.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    public val sortOptions: ImmutableList<SortOption> = SortOption.entries.toImmutableList()
    public val currentLocalDate: LocalDate = dateTimeUtil.getCurrentLocalDate()

    public fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateTransactionForValuesInTransactions(
        selectedTransactions: List<Int>,
        transactionForId: Int,
    ) {
        viewModelScope.launch {
            val updatedTransactions = allTransactionData.value.map { transactionData ->
                transactionData.transaction
            }.filter {
                it.transactionType == TransactionType.EXPENSE &&
                        selectedTransactions.contains(it.id)
            }.map {
                it.copy(
                    transactionForId = transactionForId,
                )
            }
            updateTransactionsUseCase(
                transactions = updatedTransactions.toTypedArray(),
            )
        }
    }
}
