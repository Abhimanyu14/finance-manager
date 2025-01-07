package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import javax.inject.Inject

public class CheckIfCategoryIsUsedInTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        categoryId: Int,
    ): Boolean {
        return transactionRepository.checkIfCategoryIsUsedInTransactions(
            categoryId = categoryId,
        )
    }
}
