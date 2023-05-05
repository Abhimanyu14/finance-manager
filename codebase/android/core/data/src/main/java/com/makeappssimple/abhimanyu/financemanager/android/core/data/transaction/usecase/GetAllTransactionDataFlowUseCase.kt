package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionDataFlowUseCase {
    operator fun invoke(): Flow<List<TransactionData>>
}

class GetAllTransactionDataFlowUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionDataFlowUseCase {
    override operator fun invoke(): Flow<List<TransactionData>> {
        return transactionRepository.getAllTransactionDataFlow()
    }
}
