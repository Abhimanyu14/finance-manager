package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

public interface InsertTransactionForValuesUseCase {
    public suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): List<Long>
}

public class InsertTransactionForValuesUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) : InsertTransactionForValuesUseCase {
    override suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): List<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.insertTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
