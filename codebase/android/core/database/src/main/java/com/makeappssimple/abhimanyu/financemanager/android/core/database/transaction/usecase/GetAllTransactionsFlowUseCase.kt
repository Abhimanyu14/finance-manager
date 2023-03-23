package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionsFlowUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetAllTransactionsFlowFlowUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionsFlowUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.getAllTransactionsFlow()
    }
}
