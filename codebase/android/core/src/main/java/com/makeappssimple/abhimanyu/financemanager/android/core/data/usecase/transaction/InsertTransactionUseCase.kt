package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

public class InsertTransactionUseCase @Inject constructor(
    private val dateTimeKit: DateTimeKit,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) {
    public suspend operator fun invoke(
        selectedAccountFrom: Account?,
        selectedAccountTo: Account?,
        selectedCategoryId: Int?,
        selectedTransactionForId: Int,
        selectedTransactionDate: LocalDate,
        selectedTransactionTime: LocalTime,
        enteredAmountValue: Long,
        enteredTitle: String,
        selectedTransactionType: TransactionType,
        originalTransaction: Transaction?,
    ): Boolean {
        val amount = Amount(
            value = enteredAmountValue,
        )
        val categoryId = getCategoryId(
            selectedTransactionType = selectedTransactionType,
            selectedCategoryId = selectedCategoryId,
        )
        val accountFromId = getAccountFromId(
            selectedTransactionType = selectedTransactionType,
            selectedAccountFrom = selectedAccountFrom,
        )
        val accountToId = getAccountToId(
            selectedTransactionType = selectedTransactionType,
            selectedAccountTo = selectedAccountTo,
        )
        val title = getTitle(
            selectedTransactionType = selectedTransactionType,
            enteredTitle = enteredTitle,
        )
        val transactionTimestamp = dateTimeKit.getTimestamp(
            date = selectedTransactionDate,
            time = selectedTransactionTime,
        )
        val transactionForId: Int = getTransactionForId(
            selectedTransactionType = selectedTransactionType,
            selectedTransactionForId = selectedTransactionForId,
        )
        val accountFrom = getAccount(
            accountId = accountFromId,
            selectedAccount = selectedAccountFrom,
        )
        val accountTo = getAccount(
            accountId = accountToId,
            selectedAccount = selectedAccountTo,
        )
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = originalTransaction?.id,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = dateTimeKit.getCurrentTimeMillis(),
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = selectedTransactionType,
        )
        myPreferencesRepository.setLastDataChangeTimestamp()
        val id = transactionRepository.insertTransaction(
            amountValue = enteredAmountValue,
            accountFrom = accountFrom,
            accountTo = accountTo,
            transaction = transaction,
        )
        updateTransactionForRefundTransaction(
            id = id,
            originalTransaction = originalTransaction,
        )
        return id != -1L
    }

    private fun getCategoryId(
        selectedTransactionType: TransactionType,
        selectedCategoryId: Int?,
    ): Int? {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                selectedCategoryId
            }

            TransactionType.EXPENSE -> {
                selectedCategoryId
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                selectedCategoryId
            }

            TransactionType.REFUND -> {
                selectedCategoryId
            }
        }
    }

    private fun getAccountFromId(
        selectedTransactionType: TransactionType,
        selectedAccountFrom: Account?,
    ): Int? {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                selectedAccountFrom?.id
            }

            TransactionType.TRANSFER -> {
                selectedAccountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                selectedAccountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
    }

    private fun getAccountToId(
        selectedTransactionType: TransactionType,
        selectedAccountTo: Account?,
    ): Int? {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                selectedAccountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                selectedAccountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                selectedAccountTo?.id
            }
        }
    }

    private fun getTitle(
        selectedTransactionType: TransactionType,
        enteredTitle: String,
    ): String {
        return when (selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                enteredTitle.capitalizeWords()
            }
        }
    }

    private fun getTransactionForId(
        selectedTransactionType: TransactionType,
        selectedTransactionForId: Int,
    ): Int {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                1
            }

            TransactionType.EXPENSE -> {
                selectedTransactionForId
            }

            TransactionType.TRANSFER -> {
                1
            }

            TransactionType.ADJUSTMENT -> {
                1
            }

            TransactionType.INVESTMENT -> {
                1
            }

            TransactionType.REFUND -> {
                1
            }
        }
    }

    private fun getAccount(
        accountId: Int?,
        selectedAccount: Account?,
    ): Account? {
        return if (accountId.isNotNull()) {
            selectedAccount
        } else {
            null
        }
    }

    private suspend fun updateTransactionForRefundTransaction(
        id: Long,
        originalTransaction: Transaction?,
    ) {
        originalTransaction?.let {
            val refundTransactionIds = originalTransaction.refundTransactionIds.orEmpty()
                .toMutableList()
            refundTransactionIds.add(id.toInt())
            updateTransactionUseCase(
                originalTransaction = originalTransaction,
                updatedTransaction = originalTransaction
                    .copy(
                        refundTransactionIds = refundTransactionIds,
                    ),
            )
        }
    }
}
