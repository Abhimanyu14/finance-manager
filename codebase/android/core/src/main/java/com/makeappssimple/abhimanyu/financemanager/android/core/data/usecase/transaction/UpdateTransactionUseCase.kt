package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import javax.inject.Inject

public class UpdateTransactionUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        @Suppress("UNUSED_PARAMETER") originalTransaction: Transaction,
        updatedTransaction: Transaction,
    ) {
        myPreferencesRepository.updateLastDataChangeTimestamp()
        transactionRepository.updateTransaction(
            transaction = updatedTransaction,
        )
    }
}
