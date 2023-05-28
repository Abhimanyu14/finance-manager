package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface RecalculateTotalUseCase {
    suspend operator fun invoke()
}

class RecalculateTotalUseCaseImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val getAllSourcesUseCase: GetAllSourcesUseCase,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : RecalculateTotalUseCase {
    override suspend operator fun invoke() {
        coroutineScope {
            val deferredList = awaitAll(
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllSourcesUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllTransactionDataUseCase()
                },
            )

            val allSources: List<Source> = deferredList[0].filterIsInstance<Source>()
            val allTransactionData: List<TransactionData> =
                deferredList[1].filterIsInstance<TransactionData>()

            myPreferencesRepository.setLastDataChangeTimestamp()
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
