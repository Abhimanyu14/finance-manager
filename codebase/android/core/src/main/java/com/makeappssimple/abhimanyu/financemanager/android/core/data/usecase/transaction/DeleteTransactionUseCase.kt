package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import javax.inject.Inject

public class DeleteTransactionUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Boolean {
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return transactionRepository.deleteTransaction(
            id = id,
        )
    }
}
