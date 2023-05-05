package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface GetAllTransactionsUseCase {
    suspend operator fun invoke(): List<Transaction>
}

class GetAllTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionsUseCase {
    override suspend operator fun invoke(): List<Transaction> {
        return transactionRepository.getAllTransactions()
    }
}
