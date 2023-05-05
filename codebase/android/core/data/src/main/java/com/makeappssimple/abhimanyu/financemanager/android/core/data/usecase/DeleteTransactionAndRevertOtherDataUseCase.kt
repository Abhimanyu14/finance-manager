package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.updateBalanceAmount
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
