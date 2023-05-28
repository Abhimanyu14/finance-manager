package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

interface UpdateTransactionForValuesUseCase {
    suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    )
}

class UpdateTransactionForValuesUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) : UpdateTransactionForValuesUseCase {
    override suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.updateTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
