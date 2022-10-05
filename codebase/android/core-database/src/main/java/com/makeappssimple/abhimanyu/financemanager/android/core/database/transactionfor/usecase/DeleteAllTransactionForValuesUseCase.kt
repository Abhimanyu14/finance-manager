package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository

interface DeleteAllTransactionForValuesUseCase {
    suspend operator fun invoke()
}

class DeleteAllTransactionForValuesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionForRepository: TransactionForRepository,
) : DeleteAllTransactionForValuesUseCase {
    override suspend operator fun invoke() {
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return transactionForRepository.deleteAllTransactionForValues()
    }
}
