package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

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
