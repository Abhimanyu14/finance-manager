package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface GetCurrentMonthTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetCurrentMonthTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetCurrentMonthTransactionsUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.getCurrentMonthTransactions()
    }
}
