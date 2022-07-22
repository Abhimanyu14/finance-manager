package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
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
