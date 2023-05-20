package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

interface GetTransactionDataMappedByCategoryUseCase {
    suspend operator fun invoke(
        transactionType: TransactionType,
    ): Map<CategoryEntity?, Long>
}

class GetTransactionDataMappedByCategoryUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionDataMappedByCategoryUseCase {
    override suspend operator fun invoke(
        transactionType: TransactionType,
    ): Map<CategoryEntity?, Long> {
        return transactionRepository.getTransactionDataMappedByCategory(
            transactionType = transactionType,
        )
    }
}
