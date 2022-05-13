package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface GetTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionsUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.transactions
    }
}
