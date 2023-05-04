package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface RecalculateTotalUseCase {
    suspend operator fun invoke()
}

class RecalculateTotalUseCaseImpl(
    getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val dataStore: MyDataStore,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : RecalculateTotalUseCase {
    private val allSources: Flow<List<Source>> = getAllSourcesFlowUseCase()
    private val allTransactionData: Flow<List<TransactionData>> = getAllTransactionDataFlowUseCase()

    override suspend operator fun invoke() {
        dataStore.setLastDataChangeTimestamp()
        val sourceBalances = hashMapOf<Int, Long>()
        val allSourcesValue = allSources.first()
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
        val updatesSources = allSourcesValue.map {
            it.updateBalanceAmount(
                updatedBalanceAmount = sourceBalances[it.id] ?: 0L,
            )
        }
        updateSourcesUseCase(
            sources = updatesSources.toTypedArray(),
        )
    }
}
