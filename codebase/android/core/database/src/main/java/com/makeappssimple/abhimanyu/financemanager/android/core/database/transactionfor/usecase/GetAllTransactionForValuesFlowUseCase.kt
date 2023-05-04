package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
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
