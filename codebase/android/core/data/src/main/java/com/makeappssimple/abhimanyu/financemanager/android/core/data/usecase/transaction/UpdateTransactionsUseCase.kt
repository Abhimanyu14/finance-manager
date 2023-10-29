package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface UpdateTransactionsUseCase {
    suspend operator fun invoke(
        vararg transactions: Transaction,
    )
}

class UpdateTransactionsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : UpdateTransactionsUseCase {
    override suspend operator fun invoke(
        vararg transactions: Transaction,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        transactionRepository.updateTransactions(
            transactions = transactions,
        )
    }
}
