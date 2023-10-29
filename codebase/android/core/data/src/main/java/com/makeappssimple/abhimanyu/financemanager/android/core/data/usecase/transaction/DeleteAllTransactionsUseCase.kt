package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository

interface DeleteAllTransactionsUseCase {
    suspend operator fun invoke()
}

class DeleteAllTransactionsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : DeleteAllTransactionsUseCase {
    override suspend operator fun invoke() {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.deleteAllTransactions()
    }
}
