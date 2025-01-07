package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import javax.inject.Inject

public class UpdateTransactionForValuesUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.updateTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
