package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow

public interface GetAllTransactionForValuesFlowUseCase {
    public operator fun invoke(): Flow<List<TransactionFor>>
}

public class GetAllTransactionForValuesFlowUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : GetAllTransactionForValuesFlowUseCase {
    override operator fun invoke(): Flow<List<TransactionFor>> {
        return transactionForRepository.getAllTransactionForValuesFlow()
    }
}
