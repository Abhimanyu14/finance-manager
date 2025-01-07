package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllTransactionDataFlowUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public operator fun invoke(): Flow<ImmutableList<TransactionData>> {
        return transactionRepository.getAllTransactionDataFlow()
    }
}
