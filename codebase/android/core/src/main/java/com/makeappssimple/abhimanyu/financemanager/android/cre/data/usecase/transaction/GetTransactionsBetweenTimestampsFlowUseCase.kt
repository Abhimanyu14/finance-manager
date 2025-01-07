package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Transaction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetTransactionsBetweenTimestampsFlowUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public operator fun invoke(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>> {
        return transactionRepository.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }
}
