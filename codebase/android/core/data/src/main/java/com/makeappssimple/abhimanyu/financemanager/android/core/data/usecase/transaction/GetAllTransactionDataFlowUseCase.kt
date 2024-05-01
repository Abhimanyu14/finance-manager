package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllTransactionDataFlowUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public operator fun invoke(): Flow<List<TransactionData>> {
        return transactionRepository.getAllTransactionDataFlow()
    }
}
