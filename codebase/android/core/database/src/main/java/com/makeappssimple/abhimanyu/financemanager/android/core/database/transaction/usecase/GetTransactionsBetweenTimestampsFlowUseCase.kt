package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetTransactionsBetweenTimestampsFlowUseCase {
    operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>
}

class GetTransactionsBetweenTimestampsFlowUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionsBetweenTimestampsFlowUseCase {
    override operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }
}
