package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository

interface GetTransactionForUseCase {
    suspend operator fun invoke(
        id: Int,
    ): TransactionFor?
}

class GetTransactionForUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : GetTransactionForUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): TransactionFor? {
        return transactionForRepository.getTransactionFor(
            id = id,
        )
    }
}
