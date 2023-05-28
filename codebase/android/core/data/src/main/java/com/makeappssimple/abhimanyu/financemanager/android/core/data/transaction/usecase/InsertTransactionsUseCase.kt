package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface InsertTransactionsUseCase {
    suspend operator fun invoke(
        vararg transactions: Transaction,
    )
}

class InsertTransactionsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : InsertTransactionsUseCase {
    override suspend operator fun invoke(
        vararg transactions: Transaction,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.insertTransactions(
            transactions = transactions,
        )
    }
}
