package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface GetTransactionDataUseCase {
    suspend operator fun invoke(
        id: Int,
    ): TransactionData?
}

class GetTransactionDataUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionDataUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): TransactionData? {
        return transactionRepository.getTransactionData(
            id = id,
        )
    }
}
