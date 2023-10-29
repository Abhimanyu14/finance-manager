package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository

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
