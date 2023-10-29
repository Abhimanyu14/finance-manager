package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

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
