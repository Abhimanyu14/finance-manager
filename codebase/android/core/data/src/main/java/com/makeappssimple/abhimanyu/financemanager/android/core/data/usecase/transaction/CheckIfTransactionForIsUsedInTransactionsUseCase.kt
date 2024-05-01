package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import javax.inject.Inject

public class CheckIfTransactionForIsUsedInTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        transactionForId: Int,
    ): Boolean {
        return transactionRepository.checkIfTransactionForIsUsedInTransactions(
            transactionForId = transactionForId,
        )
    }
}
