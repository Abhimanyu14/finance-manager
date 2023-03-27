package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository

interface GetAllTransactionForValuesUseCase {
    suspend operator fun invoke(): List<TransactionFor>
}

class GetAllTransactionForValuesUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : GetAllTransactionForValuesUseCase {
    override suspend operator fun invoke(): List<TransactionFor> {
        return transactionForRepository.getAllTransactionForValues()
    }
}
