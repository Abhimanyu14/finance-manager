package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface UpdateTransactionUseCase {
    suspend operator fun invoke(
        originalTransaction: Transaction,
        updatedTransaction: Transaction,
    )
}

class UpdateTransactionUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : UpdateTransactionUseCase {
    override suspend operator fun invoke(
        originalTransaction: Transaction,
        updatedTransaction: Transaction,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        transactionRepository.updateTransaction(
            transaction = updatedTransaction,
        )
    }
}
