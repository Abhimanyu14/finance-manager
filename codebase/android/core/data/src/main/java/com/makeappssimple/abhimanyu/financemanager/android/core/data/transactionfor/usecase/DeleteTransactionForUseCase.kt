package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository

interface DeleteTransactionForUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteTransactionForUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) : DeleteTransactionForUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.deleteTransactionFor(
            id = id,
        )
    }
}
