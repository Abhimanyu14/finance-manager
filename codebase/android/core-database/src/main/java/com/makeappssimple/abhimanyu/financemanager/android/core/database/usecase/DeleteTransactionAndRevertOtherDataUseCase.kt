package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
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
