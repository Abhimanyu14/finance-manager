package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository

interface DeleteTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Boolean
}

class DeleteTransactionUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : DeleteTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.deleteTransaction(
            id = id,
        )
    }
}
