package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType

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
        val sourceFromId = transaction.sourceFromId
        val sourceToId = transaction.sourceToId
        when (transaction.transactionType) {
            TransactionType.INCOME -> {
                handleNonTransferTransactionsSourceTo(
                    sourceToId = sourceToId,
                    transaction = transaction,
                )
            }
            TransactionType.EXPENSE -> {
                handleNonTransferTransactionsSourceFrom(
                    sourceFromId = sourceFromId,
                    transaction = transaction
                )
            }
            TransactionType.TRANSFER -> {
                if (
                    sourceFromId != null &&
                    sourceToId != null
                ) {
                    handleTransferTransactions(
                        sourceFromId = sourceFromId,
                        sourceToId = sourceToId,
                        transaction = transaction,
                    )
                }
            }
            TransactionType.ADJUSTMENT -> {
                handleNonTransferTransactionsSourceTo(
                    sourceToId = sourceToId,
                    transaction = transaction,
                )
            }
        }
    }

    private suspend fun handleTransferTransactions(
        sourceFromId: Int,
        sourceToId: Int,
        transaction: Transaction,
    ) {
        handleTransferTransactionsSourceFrom(
            sourceFromId = sourceFromId,
            transaction = transaction,
        )
        handleTransferTransactionsSourceTo(
            sourceToId = sourceToId,
            transaction = transaction,
        )
    }

    private suspend fun handleTransferTransactionsSourceFrom(
        sourceFromId: Int,
        transaction: Transaction,
    ) {
        val source = getSourceUseCase(
            id = sourceFromId,
        ) ?: return
        updateSourcesUseCase(
            source.copy(
                balanceAmount = source.balanceAmount.copy(
                    value = source.balanceAmount.value + transaction.amount.value,
                )
            ),
        )
    }

    private suspend fun handleTransferTransactionsSourceTo(
        sourceToId: Int,
        transaction: Transaction,
    ) {
        // TODO-Abhi: Amount sign change
        val source = getSourceUseCase(
            id = sourceToId,
        ) ?: return
        updateSourcesUseCase(
            source.copy(
                balanceAmount = source.balanceAmount.copy(
                    value = source.balanceAmount.value - transaction.amount.value,
                ),
            ),
        )
    }

    private suspend fun handleNonTransferTransactionsSourceFrom(
        sourceFromId: Int?,
        transaction: Transaction,
    ) {
        // TODO-Abhi: Amount sign change
        sourceFromId ?: return
        val source = getSourceUseCase(
            id = sourceFromId,
        ) ?: return
        updateSourcesUseCase(
            source.copy(
                balanceAmount = source.balanceAmount.copy(
                    value = source.balanceAmount.value - transaction.amount.value,
                )
            ),
        )
    }

    private suspend fun handleNonTransferTransactionsSourceTo(
        sourceToId: Int?,
        transaction: Transaction,
    ) {
        // TODO-Abhi: Amount sign change
        sourceToId ?: return
        val source = getSourceUseCase(
            id = sourceToId,
        ) ?: return
        updateSourcesUseCase(
            source.copy(
                balanceAmount = source.balanceAmount.copy(
                    value = source.balanceAmount.value - transaction.amount.value,
                )
            ),
        )
    }
}
