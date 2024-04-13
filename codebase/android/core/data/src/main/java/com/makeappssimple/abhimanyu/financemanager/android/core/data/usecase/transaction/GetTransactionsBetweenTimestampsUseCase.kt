package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

public interface GetTransactionsBetweenTimestampsUseCase {
    public suspend operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction>
}

public class GetTransactionsBetweenTimestampsUseCaseImpl(
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
