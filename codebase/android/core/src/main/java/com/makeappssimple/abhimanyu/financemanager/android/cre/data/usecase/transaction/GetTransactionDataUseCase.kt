package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import javax.inject.Inject

public class GetTransactionDataUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): TransactionData? {
        return transactionRepository.getTransactionData(
            id = id,
        )
    }
}
