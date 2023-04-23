package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteTransactionAndRevertOtherDataUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteTransactionAndRevertOtherDataUseCaseImpl(
    private val dataStore: MyDataStore,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
) : DeleteTransactionAndRevertOtherDataUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        dataStore.setLastDataChangeTimestamp()
        val transactionData = getTransactionDataUseCase(
            id = id,
        ) ?: return
        val updatesSources = mutableListOf<Source>()
        transactionData.sourceFrom?.let {
            updatesSources.add(
                it.updateBalanceAmount(
                    updatedBalanceAmount = it.balanceAmount.value + transactionData.transaction.amount.value,
                )
            )
        }
        transactionData.sourceTo?.let {
            updatesSources.add(
                it.updateBalanceAmount(
                    updatedBalanceAmount = it.balanceAmount.value - transactionData.transaction.amount.value,
                )
            )
        }
        deleteTransactionUseCase(
            id = id,
            sources = updatesSources.toTypedArray(),
        )
    }
}
