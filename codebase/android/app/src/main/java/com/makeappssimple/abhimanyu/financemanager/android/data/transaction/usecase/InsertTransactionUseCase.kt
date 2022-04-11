package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        transaction: Transaction,
    ) {
        return transactionRepository.insertTransaction(
            transaction = transaction,
        )
    }
}
