package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction

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
