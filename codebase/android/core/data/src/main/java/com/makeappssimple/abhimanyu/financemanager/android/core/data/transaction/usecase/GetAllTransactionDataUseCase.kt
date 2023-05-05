package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData

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
