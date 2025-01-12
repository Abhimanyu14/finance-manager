package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class InsertTransactionForValuesUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long> {
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return transactionForRepository.insertTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
