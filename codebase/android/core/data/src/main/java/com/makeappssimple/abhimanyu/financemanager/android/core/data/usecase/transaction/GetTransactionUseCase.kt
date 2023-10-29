package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface GetTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Transaction?
}

class GetTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Transaction? {
        return transactionRepository.getTransaction(
            id = id,
        )
    }
}
