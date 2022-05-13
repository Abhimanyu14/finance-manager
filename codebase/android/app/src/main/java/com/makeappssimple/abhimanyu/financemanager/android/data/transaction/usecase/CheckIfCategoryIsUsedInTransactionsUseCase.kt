package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository

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
