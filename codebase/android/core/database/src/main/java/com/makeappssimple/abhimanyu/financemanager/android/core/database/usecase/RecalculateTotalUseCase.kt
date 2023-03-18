package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface RecalculateTotalUseCase {
    suspend operator fun invoke()
}

class RecalculateTotalUseCaseImpl(
    getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    private val dataStore: MyDataStore,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : RecalculateTotalUseCase {
    private val sources: Flow<List<Source>> = getSourcesUseCase()
    private val allTransactionData: Flow<List<TransactionData>> = getAllTransactionDataUseCase()

    override suspend operator fun invoke() {
        dataStore.updateLastDataChangeTimestamp()
        val sourceBalances = hashMapOf<Int, Long>()
        val sourcesValue = sources.first()
        val allTransactionDataValue = allTransactionData.first()
        allTransactionDataValue.forEach { transactionData ->
            transactionData.sourceFrom?.let {
                sourceBalances[it.id] =
                    (sourceBalances[it.id] ?: 0L) - transactionData.transaction.amount.value
            }
            transactionData.sourceTo?.let {
                sourceBalances[it.id] =
                    (sourceBalances[it.id] ?: 0L) + transactionData.transaction.amount.value
            }
        }
        val updatesSources = sourcesValue.map {
            it.updateBalanceAmount(
                updatedBalanceAmount = sourceBalances[it.id] ?: 0L,
            )
        }
        updateSourcesUseCase(
            sources = updatesSources.toTypedArray(),
        )
    }
}
