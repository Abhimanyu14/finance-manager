package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import javax.inject.Inject

public class GetTransactionsBetweenTimestampsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return transactionRepository.getTransactionsBetweenTimestamps(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }
}
