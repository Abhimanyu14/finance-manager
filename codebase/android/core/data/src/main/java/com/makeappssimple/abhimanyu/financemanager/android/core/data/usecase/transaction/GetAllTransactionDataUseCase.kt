package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

interface GetAllTransactionDataUseCase {
    suspend operator fun invoke(): List<TransactionData>
}

class GetAllTransactionDataUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionDataUseCase {
    override suspend operator fun invoke(): List<TransactionData> {
        return transactionRepository.getAllTransactionData()
    }
}
