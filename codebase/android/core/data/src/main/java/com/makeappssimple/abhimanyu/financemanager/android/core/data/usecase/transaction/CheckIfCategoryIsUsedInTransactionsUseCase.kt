package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository

public interface CheckIfCategoryIsUsedInTransactionsUseCase {
    public suspend operator fun invoke(
        categoryId: Int,
    ): Boolean
}

public class CheckIfCategoryIsUsedInTransactionsUseCaseImpl(
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
