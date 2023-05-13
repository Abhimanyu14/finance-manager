package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface GetTransactionsBetweenTimestampsUseCase {
    suspend operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction>
}

class GetTransactionsBetweenTimestampsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionsBetweenTimestampsUseCase {
    override suspend operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return transactionRepository.getTransactionsBetweenTimestamps(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }
}
