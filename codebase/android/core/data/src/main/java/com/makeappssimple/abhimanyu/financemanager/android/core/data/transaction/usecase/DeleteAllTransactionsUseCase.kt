package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository

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
