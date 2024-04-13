package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

public interface UpdateTransactionForValuesUseCase {
    public suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): Boolean
}

public class UpdateTransactionForValuesUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionForRepository: TransactionForRepository,
) : UpdateTransactionForValuesUseCase {
    override suspend operator fun invoke(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionForRepository.updateTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }
}
