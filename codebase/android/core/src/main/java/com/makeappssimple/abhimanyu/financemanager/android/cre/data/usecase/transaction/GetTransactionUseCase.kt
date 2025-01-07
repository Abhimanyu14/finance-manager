package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Transaction
import javax.inject.Inject

public class GetTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Transaction? {
        return transactionRepository.getTransaction(
            id = id,
        )
    }
}
