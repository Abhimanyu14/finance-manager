package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
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
        val updatesSources = buildList {
            transactionData.sourceFrom?.let {
                add(
                    it.updateBalanceAmount(
                        updatedBalanceAmount = it.balanceAmount.value + transactionData.transaction.amount.value,
                    )
                )
            }
            transactionData.sourceTo?.let {
                add(
                    it.updateBalanceAmount(
                        updatedBalanceAmount = it.balanceAmount.value - transactionData.transaction.amount.value,
                    )
                )
            }
        }
        deleteTransactionUseCase(
            id = id,
            sources = updatesSources.toTypedArray(),
        )
    }
}
