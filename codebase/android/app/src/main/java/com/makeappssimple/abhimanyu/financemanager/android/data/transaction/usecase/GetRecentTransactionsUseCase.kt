package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetRecentTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(
        numberOfTransactions: Int = 10,
    ): Flow<List<Transaction>> {
        return transactionRepository.getRecentTransactions(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
