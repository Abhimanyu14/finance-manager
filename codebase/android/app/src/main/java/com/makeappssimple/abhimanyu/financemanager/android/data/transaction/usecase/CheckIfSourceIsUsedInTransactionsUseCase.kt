package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import javax.inject.Inject

open class CheckIfSourceIsUsedInTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        sourceId: Int,
    ): Boolean {
        return transactionRepository.checkIfSourceIsUsedInTransactions(
            sourceId = sourceId,
        )
    }
}
