package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionDetail
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetRecentTransactionDetailsUseCase {
    operator fun invoke(
        numberOfTransactions: Int = 10,
    ): Flow<List<TransactionDetail>>
}

class GetRecentTransactionDetailsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetRecentTransactionDetailsUseCase {
    override operator fun invoke(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDetail>> {
        return transactionRepository.getRecentTransactionDetails(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
