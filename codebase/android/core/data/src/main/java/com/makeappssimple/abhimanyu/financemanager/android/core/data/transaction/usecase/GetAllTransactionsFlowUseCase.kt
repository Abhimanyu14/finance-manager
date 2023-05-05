package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionsFlowUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class GetAllTransactionsFlowUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionsFlowUseCase {
    override operator fun invoke(): Flow<List<Transaction>> {
        return transactionRepository.getAllTransactionsFlow()
    }
}
