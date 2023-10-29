package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
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
