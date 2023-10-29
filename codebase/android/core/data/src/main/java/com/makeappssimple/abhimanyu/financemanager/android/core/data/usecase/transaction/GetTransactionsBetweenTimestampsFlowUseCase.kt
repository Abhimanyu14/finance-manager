package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
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
