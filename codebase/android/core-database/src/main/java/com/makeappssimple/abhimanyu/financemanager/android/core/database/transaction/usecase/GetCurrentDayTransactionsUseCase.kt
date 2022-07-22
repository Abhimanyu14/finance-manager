package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
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
