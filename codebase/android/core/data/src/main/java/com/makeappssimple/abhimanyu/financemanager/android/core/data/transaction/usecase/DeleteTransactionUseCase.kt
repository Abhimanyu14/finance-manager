package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository

interface DeleteTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteTransactionUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : DeleteTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.deleteTransaction(
            id = id,
        )
    }
}
