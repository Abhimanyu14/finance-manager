package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import kotlinx.coroutines.flow.Flow

interface GetAllTransactionForValuesUseCase {
    operator fun invoke(): Flow<List<TransactionFor>>
}

class GetAllTransactionForValuesUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : GetAllTransactionForValuesUseCase {
    override operator fun invoke(): Flow<List<TransactionFor>> {
        return transactionForRepository.transactionForValues
    }
}
