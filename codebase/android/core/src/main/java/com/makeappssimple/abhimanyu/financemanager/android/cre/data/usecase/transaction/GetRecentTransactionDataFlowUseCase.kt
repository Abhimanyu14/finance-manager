package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private object GetRecentTransactionDataFlowUseCaseConstants {
    const val DEFAULT_NUMBER_OF_RECENT_TRANSACTIONS = 10
}

public class GetRecentTransactionDataFlowUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public operator fun invoke(
        numberOfTransactions: Int = GetRecentTransactionDataFlowUseCaseConstants.DEFAULT_NUMBER_OF_RECENT_TRANSACTIONS,
    ): Flow<ImmutableList<TransactionData>> {
        return transactionRepository.getRecentTransactionDataFlow(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
