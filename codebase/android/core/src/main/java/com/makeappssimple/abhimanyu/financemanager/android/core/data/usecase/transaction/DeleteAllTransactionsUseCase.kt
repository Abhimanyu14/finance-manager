package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import javax.inject.Inject

public class DeleteAllTransactionsUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(): Boolean {
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return transactionRepository.deleteAllTransactions()
    }
}
