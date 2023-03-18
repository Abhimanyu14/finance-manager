package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionDataUseCase {
    operator fun invoke(): Flow<List<TransactionData>>
}

class GetAllTransactionDataUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionDataUseCase {
    override operator fun invoke(): Flow<List<TransactionData>> {
        return transactionRepository.getAllTransactionData()
    }
}
