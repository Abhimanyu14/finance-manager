package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetAllTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionsUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.allTransactions
    }
}
