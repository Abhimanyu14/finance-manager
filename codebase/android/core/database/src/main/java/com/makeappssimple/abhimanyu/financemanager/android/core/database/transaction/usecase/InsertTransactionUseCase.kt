package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface InsertTransactionUseCase {
    suspend operator fun invoke(
        amountValue: Long,
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
    ): Long
}

class InsertTransactionUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
) : InsertTransactionUseCase {
    override suspend operator fun invoke(
        amountValue: Long,
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
    ): Long {
        dataStore.setLastDataChangeTimestamp()
        return transactionRepository.insertTransaction(
            amountValue = amountValue,
            sourceFrom = sourceFrom,
            sourceTo = sourceTo,
            transaction = transaction,
        )
    }
}
