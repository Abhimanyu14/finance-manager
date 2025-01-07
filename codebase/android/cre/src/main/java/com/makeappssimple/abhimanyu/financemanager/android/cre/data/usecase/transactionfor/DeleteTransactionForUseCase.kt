package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepository
import javax.inject.Inject

public class DeleteTransactionForUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.deleteTransactionFor(
            id = id,
        )
    }
}
