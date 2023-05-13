package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

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
