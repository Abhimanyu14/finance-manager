package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class InsertTransactionForValuesUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.insertTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
