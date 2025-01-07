package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Transaction
import javax.inject.Inject

public class UpdateTransactionUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        @Suppress("UNUSED_PARAMETER") originalTransaction: Transaction,
        updatedTransaction: Transaction,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        transactionRepository.updateTransaction(
            transaction = updatedTransaction,
        )
    }
}
