package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteAllTransactionForValuesUseCase {
    suspend operator fun invoke()
}

class DeleteAllTransactionForValuesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionForRepository: TransactionForRepository,
) : DeleteAllTransactionForValuesUseCase {
    override suspend operator fun invoke() {
        dataStore.updateLastDataChangeTimestamp()
        return transactionForRepository.deleteAllTransactionForValues()
    }
}
