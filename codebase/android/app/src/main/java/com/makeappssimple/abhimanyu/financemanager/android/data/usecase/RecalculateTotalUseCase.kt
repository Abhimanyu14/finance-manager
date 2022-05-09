package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RecalculateTotalUseCase @Inject constructor(
    getSourcesUseCase: GetSourcesUseCase,
    getTransactionsUseCase: GetTransactionsUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) {
    private val sources: Flow<List<Source>> = getSourcesUseCase()
    private val transactionsListItemViewData: Flow<List<Transaction>> = getTransactionsUseCase()

    suspend operator fun invoke() {
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

        collectedTransactions.forEach {
            when (it.transactionType) {
                TransactionType.INCOME -> {
                    it.sourceToId?.let { sourceToId ->
                        getSourceUseCase(
                            id = sourceToId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value + it.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                }
                TransactionType.EXPENSE -> {
                    it.sourceFromId?.let { sourceFromId ->
                        getSourceUseCase(
                            id = sourceFromId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value + it.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                }
                TransactionType.TRANSFER -> {
                    it.sourceFromId?.let { sourceFromId ->
                        getSourceUseCase(
                            id = sourceFromId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value - it.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                    it.sourceToId?.let { sourceToId ->
                        getSourceUseCase(
                            id = sourceToId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value + it.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                }
                TransactionType.ADJUSTMENT -> {
                    it.sourceToId?.let { sourceToId ->
                        getSourceUseCase(
                            id = sourceToId,
                        )?.let { source ->
                            updateSourcesUseCase(
                                source.copy(
                                    balanceAmount = source.balanceAmount.copy(
                                        value = source.balanceAmount.value + it.amount.value,
                                    ),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}
