package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

public interface GetTransactionForUseCase {
    public suspend operator fun invoke(
        id: Int,
    ): TransactionFor?
}

public class GetTransactionForUseCaseImpl(
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
