package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionDetail
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionDetailsUseCase {
    operator fun invoke(): Flow<List<TransactionDetail>>
}

class GetAllTransactionDetailsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetAllTransactionDetailsUseCase {
    override operator fun invoke(): Flow<List<TransactionDetail>> {
        return transactionRepository.getAllTransactionDetails()
    }
}
