package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
        vararg sources: Source,
    )
}

class DeleteTransactionUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
) : DeleteTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
        vararg sources: Source,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return transactionRepository.deleteTransaction(
            id = id,
            sources = sources,
        )
    }
}