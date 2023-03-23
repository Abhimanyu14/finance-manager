package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionDataFlowUseCase {
    operator fun invoke(): Flow<List<TransactionData>>
}

class GetAllTransactionDataFlowFlowUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionDataFlowUseCase {
    override operator fun invoke(): Flow<List<TransactionData>> {
        return transactionRepository.getAllTransactionDataFlow()
    }
}
