package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Transaction
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class InsertTransactionsUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        vararg transactions: Transaction,
    ): ImmutableList<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.insertTransactions(
            transactions = transactions,
        )
    }
}
