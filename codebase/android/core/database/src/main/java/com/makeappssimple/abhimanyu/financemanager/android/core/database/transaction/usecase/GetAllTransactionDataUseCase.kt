package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

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
