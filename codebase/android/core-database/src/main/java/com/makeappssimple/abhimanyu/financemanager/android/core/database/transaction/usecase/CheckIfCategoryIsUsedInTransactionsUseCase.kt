package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface CheckIfCategoryIsUsedInTransactionsUseCase {
    suspend operator fun invoke(
        categoryId: Int,
    ): Boolean
}

class CheckIfCategoryIsUsedInTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : CheckIfCategoryIsUsedInTransactionsUseCase {
    override suspend operator fun invoke(
        categoryId: Int,
    ): Boolean {
        return transactionRepository.checkIfCategoryIsUsedInTransactions(
            categoryId = categoryId,
        )
    }
}
