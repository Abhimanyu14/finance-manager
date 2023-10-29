package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
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
