package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import javax.inject.Inject

public class UpdateTransactionForUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        currentTransactionFor: TransactionFor,
        title: String,
    ): Boolean {
        if (title.isBlank()) {
            return false
        }
        val updatedTransactionFor = currentTransactionFor
            .copy(
                title = title,
            )
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.updateTransactionForValues(
            updatedTransactionFor,
        )
    }
}
