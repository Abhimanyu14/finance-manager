package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCase

interface DeleteTransactionAndRevertOtherDataUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteTransactionAndRevertOtherDataUseCaseImpl(
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : DeleteTransactionAndRevertOtherDataUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        val transaction = getTransactionUseCase(
            id = id,
        ) ?: return
        deleteTransactionUseCase(
            id = id,
        )
        val sourceTo = transaction.sourceToId?.let {
            getSourceUseCase(
                id = it,
            )
        }
        val sourceFrom = transaction.sourceFromId?.let {
            getSourceUseCase(
                id = it,
            )
        }

        processTransaction(
            sourceTo = sourceTo,
            sourceFrom = sourceFrom,
            transaction = transaction,
        )
    }

    private suspend fun processTransaction(
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
    ) {
        sourceFrom?.let {
            updateSourceBalanceAmount(
                source = it,
                balanceAmountValue = it.balanceAmount.value + transaction.amount.value,
            )
        }
        sourceTo?.let {
            updateSourceBalanceAmount(
                source = it,
                balanceAmountValue = it.balanceAmount.value - transaction.amount.value,
            )
        }
    }

    private suspend fun updateSourceBalanceAmount(
        source: Source,
        balanceAmountValue: Long,
    ) {
        updateSourcesUseCase(
            source.copy(
                balanceAmount = source.balanceAmount.copy(
                    value = balanceAmountValue,
                )
            ),
        )
    }
}
