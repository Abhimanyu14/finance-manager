package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetTransactionsBetweenTimestampsUseCase {
    operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>
}

class GetTransactionsBetweenTimestampsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionsBetweenTimestampsUseCase {
    override operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsBetweenTimestamps(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }
}
