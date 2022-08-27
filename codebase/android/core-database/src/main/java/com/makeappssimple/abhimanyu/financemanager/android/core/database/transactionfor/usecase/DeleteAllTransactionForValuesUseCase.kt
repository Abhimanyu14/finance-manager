package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository

interface DeleteAllTransactionForValuesUseCase {
    suspend operator fun invoke()
}

class DeleteAllTransactionForValuesUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : DeleteAllTransactionForValuesUseCase {
    override suspend operator fun invoke() {
        return transactionForRepository.deleteAllTransactionForValues()
    }
}
