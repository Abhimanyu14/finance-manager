package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository

public interface DeleteTransactionForUseCase {
    public suspend operator fun invoke(
        id: Int,
    ): Boolean
}

public class DeleteTransactionForUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) : DeleteTransactionForUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.deleteTransactionFor(
            id = id,
        )
    }
}
