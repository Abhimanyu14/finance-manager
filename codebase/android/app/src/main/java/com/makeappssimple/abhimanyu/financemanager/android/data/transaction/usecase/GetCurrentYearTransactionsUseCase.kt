package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface GetCurrentYearTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetCurrentYearTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetCurrentYearTransactionsUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.getCurrentYearTransactions()
    }
}
