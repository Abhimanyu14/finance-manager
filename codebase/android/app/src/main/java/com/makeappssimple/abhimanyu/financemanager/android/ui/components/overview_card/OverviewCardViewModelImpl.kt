package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetCurrentMonthTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Green700
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class OverviewCardViewModelImpl @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getCurrentMonthTransactionsUseCase: GetCurrentMonthTransactionsUseCase,
) : OverviewCardViewModel, ViewModel() {
    private val currentMonthTransactions: StateFlow<List<Transaction>> =
        getCurrentMonthTransactionsUseCase()
            .defaultListStateIn()
    private val incomeAmount = currentMonthTransactions
        .map { transactions ->
            transactions.filter {
                it.transactionType == TransactionType.INCOME
            }.sumOf {
                it.amount.value
            }.toFloat()
        }
        .defaultObjectStateIn()
    private val expenseAmount = currentMonthTransactions
        .map { transactions ->
            transactions.filter {
                it.transactionType == TransactionType.EXPENSE
            }.sumOf {
                it.amount.value
            }.toFloat()
        }
        .defaultObjectStateIn()
    override val pieChartData: StateFlow<PieChartData?> = combine(
        flow = incomeAmount,
        flow2 = expenseAmount,
    ) { incomeAmount, expenseAmount ->
        val totalIncomeAmount = Amount(
            value = incomeAmount?.toLong() ?: 0L,
        )
        val totalExpenseAmount = Amount(
            value = expenseAmount?.toLong() ?: 0L,
        )
        PieChartData(
            items = listOf(
                PieChartItemData(
                    value = incomeAmount ?: 10F,
                    text = "Income : $totalIncomeAmount",
                    color = Green700,
                ),
                PieChartItemData(
                    value = expenseAmount ?: 10F,
                    text = "Expense : ${totalExpenseAmount.toNonSignedString()}",
                    color = Red,
                ),
            ),
        )
    }.flowOn(
        context = dispatcherProvider.io,
    ).defaultObjectStateIn()

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
