package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionForValuesFlowUseCase {
    operator fun invoke(): Flow<List<TransactionFor>>
}

class GetAllTransactionForValuesFlowUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : GetAllTransactionForValuesFlowUseCase {
    override operator fun invoke(): Flow<List<TransactionFor>> {
        return transactionForRepository.getAllTransactionForValuesFlow()
    }
}
