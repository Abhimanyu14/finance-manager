package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
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
