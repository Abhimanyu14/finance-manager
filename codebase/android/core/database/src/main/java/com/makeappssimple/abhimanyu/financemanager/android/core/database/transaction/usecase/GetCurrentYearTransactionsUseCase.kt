package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
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
