package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@VisibleForTesting
internal const val defaultOverviewTabSelection = 1

@HiltViewModel
internal class OverviewCardViewModelImpl @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    getTransactionUseCase: GetTransactionUseCase,
) : OverviewCardViewModel, ViewModel() {
    private val _overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = defaultOverviewTabSelection,
    )
    override val overviewTabSelectionIndex: StateFlow<Int>
        get() = _overviewTabSelectionIndex

    private val transactionsBetweenTimestamps: StateFlow<List<Transaction>> =
        overviewTabSelectionIndex.map {
            val overviewTabOption = OverviewTabOption.values()[it]
            getTransactionsBetweenTimestampsUseCase(
                startingTimestamp = when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        getStartOfDayTimestamp()
                    }

                    OverviewTabOption.MONTH -> {
                        getStartOfMonthTimestamp()
                    }

                    OverviewTabOption.YEAR -> {
                        getStartOfYearTimestamp()
                    }
                },
                endingTimestamp = when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        getEndOfDayTimestamp()
                    }

                    OverviewTabOption.MONTH -> {
                        getEndOfMonthTimestamp()
                    }

                    OverviewTabOption.YEAR -> {
                        getEndOfYearTimestamp()
                    }
                },
            )
        }.flowOn(
            context = dispatcherProvider.io,
        ).defaultListStateIn(
            viewModelScope,
        )

    override val amountData: StateFlow<List<Float>?> =
        transactionsBetweenTimestamps.map { transactions ->
            val incomeAmount = transactions.filter {
                it.transactionType == TransactionType.INCOME
            }.sumOf {
                it.amount.value
            }.toFloat()

            val expenseTransactions = transactions.filter {
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
            val expenseAmount = expenseTransactionsWithRefund.sumOf { transaction ->
                if (transaction.transactionType == TransactionType.EXPENSE) {
                    abs(transaction.amount.value)
                } else {
                    -abs(transaction.amount.value)
                }
            }.toFloat()

            listOf(
                incomeAmount,
                expenseAmount,
            )
        }.defaultListStateIn(
            scope = viewModelScope,
        )

    override fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        _overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }
}
