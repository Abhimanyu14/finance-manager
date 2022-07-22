package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
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
