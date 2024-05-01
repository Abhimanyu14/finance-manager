package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetTransactionsBetweenTimestampsFlowUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }
}
