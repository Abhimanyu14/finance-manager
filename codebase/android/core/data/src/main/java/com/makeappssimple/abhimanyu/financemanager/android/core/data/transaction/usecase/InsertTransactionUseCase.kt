package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

interface InsertTransactionUseCase {
    suspend operator fun invoke(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long
}

class InsertTransactionUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : InsertTransactionUseCase {
    override suspend operator fun invoke(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return transactionRepository.insertTransaction(
            amountValue = amountValue,
            accountFrom = accountFrom,
            accountTo = accountTo,
            transaction = transaction,
        )
    }
}
