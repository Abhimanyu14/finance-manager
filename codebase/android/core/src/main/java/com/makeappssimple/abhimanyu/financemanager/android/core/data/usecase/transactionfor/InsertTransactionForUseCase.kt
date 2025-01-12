package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import javax.inject.Inject

public class InsertTransactionForUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        title: String,
    ): Long {
        if (title.isBlank()) {
            return -1
        }
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return transactionForRepository.insertTransactionForValues(
            TransactionFor(
                title = title,
            ),
        ).first()
    }
}
