package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface InsertTransactionsUseCase {
    suspend operator fun invoke(
        vararg transactions: Transaction,
    ): List<Long>
}

class InsertTransactionsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : InsertTransactionsUseCase {
    override suspend operator fun invoke(
        vararg transactions: Transaction,
    ): List<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.insertTransactions(
            transactions = transactions,
        )
    }
}
