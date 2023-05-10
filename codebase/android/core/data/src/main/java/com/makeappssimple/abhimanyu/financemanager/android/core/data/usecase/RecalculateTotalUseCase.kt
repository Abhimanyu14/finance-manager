package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface RecalculateTotalUseCase {
    suspend operator fun invoke()
}

class RecalculateTotalUseCaseImpl(
    private val dataStore: MyDataStore,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllSourcesUseCase: GetAllSourcesUseCase,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : RecalculateTotalUseCase {
    override suspend operator fun invoke() {
        coroutineScope {
            val deferredList = awaitAll(
                async(
                    dispatcherProvider.io,
                ) {
                    getAllSourcesUseCase()
                },
                async(
                    dispatcherProvider.io,
                ) {
                    getAllTransactionDataUseCase()
                },
            )

            val allSources: List<Source> = deferredList[0].filterIsInstance<Source>()
            val allTransactionData: List<TransactionData> =
                deferredList[1].filterIsInstance<TransactionData>()

            dataStore.setLastDataChangeTimestamp()
            val sourceBalances = hashMapOf<Int, Long>()
            allTransactionData.forEach { transactionData ->
                transactionData.sourceFrom?.let {
                    sourceBalances[it.id] =
                        (sourceBalances[it.id] ?: 0L) - transactionData.transaction.amount.value
                }
                transactionData.sourceTo?.let {
                    sourceBalances[it.id] =
                        (sourceBalances[it.id] ?: 0L) + transactionData.transaction.amount.value
                }
            }
            val updatesSources = allSources.map {
                it.updateBalanceAmount(
                    updatedBalanceAmount = sourceBalances[it.id] ?: 0L,
                )
            }
            updateSourcesUseCase(
                sources = updatesSources.toTypedArray(),
            )
        }
    }
}
