package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository

interface InsertTransactionForValuesUseCase {
    suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    )
}

class InsertTransactionForValuesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionForRepository: TransactionForRepository,
) : InsertTransactionForValuesUseCase {
    override suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ) {
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return transactionForRepository.insertTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
