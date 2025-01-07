package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import javax.inject.Inject

public class GetTransactionForUseCase @Inject constructor(
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): TransactionFor? {
        return transactionForRepository.getTransactionFor(
            id = id,
        )
    }
}
