package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import kotlinx.coroutines.flow.Flow

interface GetRecentTransactionDataFlowUseCase {
    operator fun invoke(
        numberOfTransactions: Int = 10,
    ): Flow<List<TransactionData>>
}

class GetRecentTransactionDataFlowUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetRecentTransactionDataFlowUseCase {
    override operator fun invoke(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return transactionRepository.getRecentTransactionDataFlow(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
