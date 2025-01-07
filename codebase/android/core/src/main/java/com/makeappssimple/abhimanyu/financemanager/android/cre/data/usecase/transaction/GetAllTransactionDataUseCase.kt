package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class GetAllTransactionDataUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(): ImmutableList<TransactionData> {
        return transactionRepository.getAllTransactionData()
    }
}
