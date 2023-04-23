package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteTransactionForUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteTransactionForUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionForRepository: TransactionForRepository,
) : DeleteTransactionForUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return transactionForRepository.deleteTransactionFor(
            id = id,
        )
    }
}
