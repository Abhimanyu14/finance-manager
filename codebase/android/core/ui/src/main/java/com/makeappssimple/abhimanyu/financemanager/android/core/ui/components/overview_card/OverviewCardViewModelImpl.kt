package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import java.time.Instant

@VisibleForTesting
internal const val defaultOverviewTabSelection = 1

internal enum class OverviewTabOption(
    val title: String,
) {
    DAY("DAY"),

    // TODO-Abhi: Enable week later
    // WEEK("Week"),
    MONTH("MONTH"),
    YEAR("YEAR"),
}

enum class OverviewCardAction {
    INCREASE,
    DECREASE,
}

data class OverviewCardData(
    val income: Float,
    val expense: Float,
    val title: String,
)

@HiltViewModel
internal class OverviewCardViewModelImpl @Inject constructor(
    dateTimeUtil: DateTimeUtil,
    getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    getTransactionUseCase: GetTransactionUseCase,
) : OverviewCardViewModel, ViewModel() {
    private val _overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = defaultOverviewTabSelection,
    )
    override val overviewTabSelectionIndex: StateFlow<Int> = _overviewTabSelectionIndex

    private val timestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = dateTimeUtil.getCurrentTimeMillis(),
    )

    override val overviewCardData: StateFlow<OverviewCardData?> = combine(
        flow = overviewTabSelectionIndex,
        flow2 = timestamp,
    ) { overviewTabSelectionIndex, timestamp ->
        val overviewTabOption = OverviewTabOption.values()[overviewTabSelectionIndex]
        val transactions = getTransactionsBetweenTimestampsUseCase(
            startingTimestamp = when (overviewTabOption) {
                OverviewTabOption.DAY -> {
                    dateTimeUtil.getStartOfDayTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.MONTH -> {
                    dateTimeUtil.getStartOfMonthTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.YEAR -> {
                    dateTimeUtil.getStartOfYearTimestamp(
                        timestamp = timestamp,
                    )
                }
            },
            endingTimestamp = when (overviewTabOption) {
                OverviewTabOption.DAY -> {
                    dateTimeUtil.getEndOfDayTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.MONTH -> {
                    dateTimeUtil.getEndOfMonthTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.YEAR -> {
                    dateTimeUtil.getEndOfYearTimestamp(
                        timestamp = timestamp,
                    )
                }
            },
        )

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

        val title = when (overviewTabOption) {
            OverviewTabOption.DAY -> {
                dateTimeUtil.getFormattedDate(timestamp).uppercase()
            }

            OverviewTabOption.MONTH -> {
                dateTimeUtil.getFormattedMonth(timestamp).uppercase()
            }

            OverviewTabOption.YEAR -> {
                dateTimeUtil.getFormattedYear(timestamp).uppercase()
            }
        }

        OverviewCardData(
            income = incomeAmount,
            expense = expenseAmount,
            title = title,
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        _overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }

    override fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    ) {
        val overviewTabOption = OverviewTabOption.values()[overviewTabSelectionIndex.value]
        when (overviewCardAction) {
            OverviewCardAction.INCREASE -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusYears(1)
                            .toEpochMilli()
                    }
                }
            }

            OverviewCardAction.DECREASE -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusYears(1)
                            .toEpochMilli()
                    }
                }
            }
        }
    }
}
