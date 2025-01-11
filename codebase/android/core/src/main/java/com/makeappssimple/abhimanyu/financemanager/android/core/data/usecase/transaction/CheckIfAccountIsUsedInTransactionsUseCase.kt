package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import javax.inject.Inject

public class CheckIfAccountIsUsedInTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        accountId: Int,
    ): Boolean {
        return transactionRepository.checkIfAccountIsUsedInTransactions(
            accountId = accountId,
        )
    }
}
