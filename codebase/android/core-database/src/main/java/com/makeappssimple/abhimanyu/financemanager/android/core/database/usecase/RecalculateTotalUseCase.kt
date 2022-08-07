package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface RecalculateTotalUseCase {
    suspend operator fun invoke()
}

class RecalculateTotalUseCaseImpl(
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : RecalculateTotalUseCase {
    private val sources: Flow<List<Source>> = getSourcesUseCase()
    private val transactionsListItemViewData: Flow<List<Transaction>> = getAllTransactionsUseCase()

    override suspend operator fun invoke() {
        resetBalanceAmountOfAllSources()

        val collectedTransactions = transactionsListItemViewData.first()
        collectedTransactions.forEach { transaction ->
            when (transaction.transactionType) {
                TransactionType.INCOME -> {
                    processIncomeTransaction(
                        transaction = transaction,
                    )
                }
                TransactionType.EXPENSE -> {
                    processExpenseTransaction(
                        transaction = transaction,
                    )
                }
                TransactionType.TRANSFER -> {
                    processTransferTransaction(
                        transaction = transaction,
                    )
                }
                TransactionType.ADJUSTMENT -> {
                    processAdjustmentTransaction(
                        transaction = transaction,
                    )
                }
            }
        }
    }

    private suspend fun resetBalanceAmountOfAllSources() {
        val collectedSources = sources.first()

        // Reset all source balance amount
        collectedSources.forEach { source ->
            resetSourceBalanceAmount(
                source = source,
            )
        }
    }

    private suspend fun resetSourceBalanceAmount(
        source: Source,
    ) {
        updateSourceBalanceAmount(
            source = source,
            balanceAmountValue = 0,
        )
    }

    private suspend fun updateSourceBalanceAmount(
        source: Source,
        balanceAmountValue: Long,
    ) {
        updateSourcesUseCase(
            source
                .copy(
                    balanceAmount = source.balanceAmount
                        .copy(
                            value = balanceAmountValue,
                        )
                ),
        )
    }

    private suspend fun processIncomeTransaction(
        transaction: Transaction,
    ) {
        val sourceId = transaction.sourceToId ?: return
        val source = getSourceUseCase(
            id = sourceId,
        ) ?: return
        updateSourceBalanceAmount(
            source = source,
            balanceAmountValue = source.balanceAmount.value + transaction.amount.value,
        )
    }

    private suspend fun processExpenseTransaction(
        transaction: Transaction,
    ) {
        val sourceId = transaction.sourceFromId ?: return
        val source = getSourceUseCase(
            id = sourceId,
        ) ?: return
        // TODO-Abhi: Amount sign change
        updateSourceBalanceAmount(
            source = source,
            balanceAmountValue = source.balanceAmount.value + transaction.amount.value,
        )
    }

    private suspend fun processTransferTransaction(
        transaction: Transaction,
    ) {
        transaction.sourceFromId?.let { sourceId ->
            getSourceUseCase(
                id = sourceId,
            )?.let { source ->
                updateSourceBalanceAmount(
                    source = source,
                    balanceAmountValue = source.balanceAmount.value - transaction.amount.value,
                )
            }
        }
        transaction.sourceToId?.let { sourceId ->
            getSourceUseCase(
                id = sourceId,
            )?.let { source ->
                updateSourceBalanceAmount(
                    source = source,
                    balanceAmountValue = source.balanceAmount.value + transaction.amount.value,
                )
            }
        }
    }

    private suspend fun processAdjustmentTransaction(
        transaction: Transaction,
    ) {
        val sourceId = transaction.sourceToId ?: return
        val source = getSourceUseCase(
            id = sourceId,
        ) ?: return
        updateSourceBalanceAmount(
            source = source,
            balanceAmountValue = source.balanceAmount.value + transaction.amount.value,
        )
    }
}
