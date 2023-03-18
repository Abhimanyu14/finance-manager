package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionsBetweenTimestampsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@HiltViewModel
internal class OverviewCardViewModelImpl @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    getTransactionUseCase: GetTransactionUseCase,
) : OverviewCardViewModel, ViewModel() {
    private val _overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 1,
    )
    override val overviewTabSelectionIndex: StateFlow<Int>
        get() = _overviewTabSelectionIndex
    private val currentDayTransactions: StateFlow<List<Transaction>> =
        getTransactionsBetweenTimestampsUseCase(
            startingTimestamp = getStartOfDayTimestamp(),
            endingTimestamp = getEndOfDayTimestamp(),
        ).defaultListStateIn(viewModelScope)
    private val currentMonthTransactions: StateFlow<List<Transaction>> =
        getTransactionsBetweenTimestampsUseCase(
            startingTimestamp = getStartOfMonthTimestamp(),
            endingTimestamp = getEndOfMonthTimestamp(),
        ).defaultListStateIn(viewModelScope)
    private val currentYearTransactions: StateFlow<List<Transaction>> =
        getTransactionsBetweenTimestampsUseCase(
            startingTimestamp = getStartOfYearTimestamp(),
            endingTimestamp = getEndOfYearTimestamp(),
        ).defaultListStateIn(viewModelScope)

    private val incomeAmount: Flow<Float?> = combine(
        flow = overviewTabSelectionIndex,
        flow2 = currentDayTransactions,
        flow3 = currentMonthTransactions,
        flow4 = currentYearTransactions,
    ) { overviewTabSelectionIndex, currentDayTransactions, currentMonthTransactions, currentYearTransactions ->
        when (OverviewTabOption.values()[overviewTabSelectionIndex]) {
            OverviewTabOption.DAY -> {
                currentDayTransactions
            }

            OverviewTabOption.MONTH -> {
                currentMonthTransactions
            }

            OverviewTabOption.YEAR -> {
                currentYearTransactions
            }
        }.filter {
            it.transactionType == TransactionType.INCOME
        }.sumOf {
            it.amount.value
        }.toFloat()
    }.flowOn(
        context = dispatcherProvider.io,
    )

    private val expenseTransactionsWithRefund: Flow<List<Transaction>> = combine(
        flow = overviewTabSelectionIndex,
        flow2 = currentDayTransactions,
        flow3 = currentMonthTransactions,
        flow4 = currentYearTransactions,
    ) { overviewTabSelectionIndex, currentDayTransactions, currentMonthTransactions, currentYearTransactions ->
        val expenseTransactions = when (OverviewTabOption.values()[overviewTabSelectionIndex]) {
            OverviewTabOption.DAY -> {
                currentDayTransactions
            }

            OverviewTabOption.MONTH -> {
                currentMonthTransactions
            }

            OverviewTabOption.YEAR -> {
                currentYearTransactions
            }
        }.filter {
            it.transactionType == TransactionType.EXPENSE
        }
        val expenseTransactionsWithRefund = mutableListOf<Transaction>()
        expenseTransactions.forEach { expenseTransaction ->
            expenseTransactionsWithRefund.add(expenseTransaction)
            expenseTransaction.refundTransactionIds?.let { refundTransactionIds ->
                refundTransactionIds.forEach { id ->
                    getTransactionUseCase(id)?.let {
                        expenseTransactionsWithRefund.add(it)
                    }
                }
            }
        }
        expenseTransactionsWithRefund
    }.flowOn(
        context = dispatcherProvider.io,
    )

    private val expenseAmount: Flow<Float?> = expenseTransactionsWithRefund.map {
        it.sumOf { transaction ->
            if (transaction.transactionType == TransactionType.EXPENSE) {
                abs(transaction.amount.value)
            } else {
                -abs(transaction.amount.value)
            }
        }.toFloat()
    }.flowOn(
        context = dispatcherProvider.io,
    )

    override val amountData: StateFlow<List<Float>?> = combine(
        flow = incomeAmount,
        flow2 = expenseAmount,
    ) { incomeAmount, expenseAmount ->
        listOf(
            incomeAmount ?: 0F,
            expenseAmount ?: 0F,
        )
    }.flowOn(
        context = dispatcherProvider.io,
    ).defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        _overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }
}
