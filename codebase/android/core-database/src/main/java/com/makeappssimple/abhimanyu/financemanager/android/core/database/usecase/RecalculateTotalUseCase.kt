package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
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
            val sourceTo = transaction.sourceToId?.let {
                getSourceUseCase(
                    id = it,
                )
            }
            val sourceFrom = transaction.sourceFromId?.let {
                getSourceUseCase(
                    id = it,
                )
            }
            processTransaction(
                sourceFrom = sourceFrom,
                sourceTo = sourceTo,
                transaction = transaction,
            )
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
            source.copy(
                balanceAmount = source.balanceAmount.copy(
                    value = balanceAmountValue,
                )
            ),
        )
    }

    private suspend fun processTransaction(
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
    ) {
        sourceFrom?.let {
            updateSourceBalanceAmount(
                source = it,
                balanceAmountValue = it.balanceAmount.value - transaction.amount.value,
            )
        }
        sourceTo?.let {
            updateSourceBalanceAmount(
                source = it,
                balanceAmountValue = it.balanceAmount.value + transaction.amount.value,
            )
        }
    }
}
