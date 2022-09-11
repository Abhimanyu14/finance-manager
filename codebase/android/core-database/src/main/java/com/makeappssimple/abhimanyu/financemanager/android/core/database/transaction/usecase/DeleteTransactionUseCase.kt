package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface DeleteTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
        vararg sources: Source,
    )
}

class DeleteTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : DeleteTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
        vararg sources: Source,
    ) {
        return transactionRepository.deleteTransaction(
            id = id,
            sources = sources,
        )
    }
}
