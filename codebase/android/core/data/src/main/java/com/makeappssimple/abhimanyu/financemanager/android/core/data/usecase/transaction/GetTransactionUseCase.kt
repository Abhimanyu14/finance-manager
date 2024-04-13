package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

public interface GetTransactionUseCase {
    public suspend operator fun invoke(
        id: Int,
    ): Transaction?
}

public class GetTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Transaction? {
        return transactionRepository.getTransaction(
            id = id,
        )
    }
}
