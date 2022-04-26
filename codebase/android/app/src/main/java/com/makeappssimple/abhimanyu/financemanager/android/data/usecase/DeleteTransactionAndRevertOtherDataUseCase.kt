package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import javax.inject.Inject

class DeleteTransactionAndRevertOtherDataUseCase @Inject constructor(
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) {
    suspend operator fun invoke(
        id: Int,
    ) {
        getTransactionUseCase(
            id = id,
        )?.let { transaction ->
            deleteTransactionUseCase(
                id = id,
            )
            val sourceFromId = transaction.sourceFromId
            val sourceToId = transaction.sourceToId
            if (
                sourceFromId != null &&
                sourceToId != null
            ) {
                handleTransferTransactions(
                    sourceFromId = sourceFromId,
                    sourceToId = sourceToId,
                    transaction = transaction,
                )
            } else {
                handleNonTransferTransactions(
                    sourceFromId = sourceFromId,
                    sourceToId = sourceToId,
                    transaction = transaction,
                )
            }
        }
    }

    private suspend fun handleNonTransferTransactions(
        sourceFromId: Int?,
        sourceToId: Int?,
        transaction: Transaction,
    ) {
        sourceFromId?.let {
            getSourceUseCase(
                id = sourceFromId,
            )?.let { sourceFrom ->
                updateSourcesUseCase(
                    sourceFrom.copy(
                        balanceAmount = sourceFrom.balanceAmount.copy(
                            value = sourceFrom.balanceAmount.value - transaction.amount.value,
                        )
                    ),
                )
            }
        }
        sourceToId?.let {
            getSourceUseCase(
                id = sourceToId,
            )?.let { sourceTo ->
                updateSourcesUseCase(
                    sourceTo.copy(
                        balanceAmount = sourceTo.balanceAmount.copy(
                            value = sourceTo.balanceAmount.value - transaction.amount.value,
                        )
                    ),
                )
            }
        }
    }

    private suspend fun handleTransferTransactions(
        sourceFromId: Int,
        sourceToId: Int,
        transaction: Transaction,
    ) {
        getSourceUseCase(
            id = sourceFromId,
        )?.let { sourceFrom ->
            updateSourcesUseCase(
                sourceFrom.copy(
                    balanceAmount = sourceFrom.balanceAmount.copy(
                        value = sourceFrom.balanceAmount.value + transaction.amount.value,
                    )
                ),
            )
        }
        getSourceUseCase(
            id = sourceToId,
        )?.let { sourceTo ->
            updateSourcesUseCase(
                sourceTo.copy(
                    balanceAmount = sourceTo.balanceAmount.copy(
                        value = sourceTo.balanceAmount.value - transaction.amount.value,
                    )
                ),
            )
        }
    }
}
