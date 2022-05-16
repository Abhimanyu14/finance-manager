package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface RecalculateTotalUseCase {
    suspend operator fun invoke()
}

class RecalculateTotalUseCaseImpl(
    getSourcesUseCase: GetSourcesUseCase,
    getTransactionsUseCase: GetTransactionsUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : RecalculateTotalUseCase {
    private val sources: Flow<List<Source>> = getSourcesUseCase()
    private val transactionsListItemViewData: Flow<List<Transaction>> = getTransactionsUseCase()

    override suspend operator fun invoke() {
        val collectedSources = sources.first()
        val collectedTransactions = transactionsListItemViewData.first()

        // Reset all source balance amount
        collectedSources.forEach {
            updateSourcesUseCase(
                it.copy(
                    balanceAmount = it.balanceAmount.copy(
                        value = 0,
                    )
                ),
            )
        }

        collectedTransactions.forEach collectedTransactionsLoop@{ transaction ->
            when (transaction.transactionType) {
                TransactionType.INCOME -> {
                    val sourceId = transaction.sourceToId ?: return@collectedTransactionsLoop
                    val source = getSourceUseCase(
                        id = sourceId,
                    ) ?: return@collectedTransactionsLoop
                    updateSourcesUseCase(
                        source.copy(
                            balanceAmount = source.balanceAmount.copy(
                                value = source.balanceAmount.value + transaction.amount.value,
                            ),
                        ),
                    )
                }
                TransactionType.EXPENSE -> {
                    val sourceId = transaction.sourceFromId ?: return@collectedTransactionsLoop
                    val source = getSourceUseCase(
                        id = sourceId,
                    ) ?: return@collectedTransactionsLoop
                    // TODO-Abhi: Amount sign change
                    updateSourcesUseCase(
                        source.copy(
                            balanceAmount = source.balanceAmount.copy(
                                value = source.balanceAmount.value + transaction.amount.value,
                            ),
                        ),
                    )
                }
                TransactionType.TRANSFER -> {
                    transaction.sourceFromId?.let { sourceId ->
                        getSourceUseCase(
                            id = sourceId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value - transaction.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                    transaction.sourceToId?.let { sourceId ->
                        getSourceUseCase(
                            id = sourceId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value + transaction.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                }
                TransactionType.ADJUSTMENT -> {
                    val sourceId = transaction.sourceToId ?: return@collectedTransactionsLoop
                    val source = getSourceUseCase(
                        id = sourceId,
                    ) ?: return@collectedTransactionsLoop
                    updateSourcesUseCase(
                        source.copy(
                            balanceAmount = source.balanceAmount.copy(
                                value = source.balanceAmount.value + transaction.amount.value,
                            ),
                        ),
                    )
                }
            }
        }
    }
}
