package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentDayTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentMonthTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentYearTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Green700
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class OverviewCardViewModelImpl @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getCurrentDayTransactionsUseCase: GetCurrentDayTransactionsUseCase,
    getCurrentMonthTransactionsUseCase: GetCurrentMonthTransactionsUseCase,
    getCurrentYearTransactionsUseCase: GetCurrentYearTransactionsUseCase,
) : OverviewCardViewModel, ViewModel() {
    private val _overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 1,
    )
    override val overviewTabSelectionIndex: StateFlow<Int>
        get() = _overviewTabSelectionIndex
    private val currentDayTransactions: StateFlow<List<Transaction>> =
        getCurrentDayTransactionsUseCase()
            .defaultListStateIn()
    private val currentMonthTransactions: StateFlow<List<Transaction>> =
        getCurrentMonthTransactionsUseCase()
            .defaultListStateIn()
    private val currentYearTransactions: StateFlow<List<Transaction>> =
        getCurrentYearTransactionsUseCase()
            .defaultListStateIn()

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

    private val expenseAmount: Flow<Float?> = combine(
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
            it.transactionType == TransactionType.EXPENSE
        }.sumOf {
            abs(it.amount.value)
        }.toFloat()
    }.flowOn(
        context = dispatcherProvider.io,
    )

    override val pieChartData: StateFlow<com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData?> = combine(
        flow = incomeAmount,
        flow2 = expenseAmount,
    ) { incomeAmount, expenseAmount ->
        val totalIncomeAmount = Amount(
            value = incomeAmount?.toLong() ?: 0L,
        )
        val totalExpenseAmount = Amount(
            value = expenseAmount?.toLong() ?: 0L,
        )
        com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData(
            items = listOf(
                com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData(
                    value = incomeAmount ?: 10F,
                    text = "Income : $totalIncomeAmount",
                    color = Green700,
                ),
                com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData(
                    value = expenseAmount ?: 10F,
                    text = "Expense : ${totalExpenseAmount.toNonSignedString()}",
                    color = Red,
                ),
            ),
        )
    }.flowOn(
        context = dispatcherProvider.io,
    ).defaultObjectStateIn()

    override fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        _overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }

    private fun <T> Flow<List<T>>.defaultListStateIn(): StateFlow<List<T>> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )
    }

    private fun <T> Flow<T>.defaultObjectStateIn(): StateFlow<T?> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null,
        )
    }
}
