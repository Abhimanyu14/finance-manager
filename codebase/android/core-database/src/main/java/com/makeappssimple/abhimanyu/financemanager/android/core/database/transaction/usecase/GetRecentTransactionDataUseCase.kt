package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetRecentTransactionDataUseCase {
    operator fun invoke(
        numberOfTransactions: Int = 10,
    ): Flow<List<TransactionData>>
}

class GetRecentTransactionDataUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetRecentTransactionDataUseCase {
    override operator fun invoke(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return transactionRepository.getRecentTransactionData(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
