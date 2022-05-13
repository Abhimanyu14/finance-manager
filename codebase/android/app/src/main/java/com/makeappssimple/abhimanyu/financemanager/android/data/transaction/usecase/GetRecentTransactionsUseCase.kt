package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface GetRecentTransactionsUseCase {
    operator fun invoke(
        numberOfTransactions: Int = 10,
    ): Flow<List<Transaction>>
}

class GetRecentTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetRecentTransactionsUseCase {
    override operator fun invoke(
        numberOfTransactions: Int,
    ): Flow<List<Transaction>> {
        return transactionRepository.getRecentTransactions(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
