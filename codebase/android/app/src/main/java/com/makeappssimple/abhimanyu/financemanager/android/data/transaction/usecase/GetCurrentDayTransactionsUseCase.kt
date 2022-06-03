package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface GetCurrentDayTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetCurrentDayTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetCurrentDayTransactionsUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.getCurrentDayTransactions()
    }
}
