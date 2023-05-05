package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor

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
