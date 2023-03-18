package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface UpdateTransactionForValuesUseCase {
    suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    )
}

class UpdateTransactionForValuesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionForRepository: TransactionForRepository,
) : UpdateTransactionForValuesUseCase {
    override suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ) {
        dataStore.updateLastDataChangeTimestamp()
        return transactionForRepository.updateTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
