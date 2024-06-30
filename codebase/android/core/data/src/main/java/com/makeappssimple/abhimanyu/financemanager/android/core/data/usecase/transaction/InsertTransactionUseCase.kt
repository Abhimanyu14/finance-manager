package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

public class InsertTransactionUseCase @Inject constructor(
    private val dateTimeUtil: DateTimeUtil,
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
    ): Long {
        val amount = Amount(
            value = enteredAmountValue,
        )
        val categoryId = when (selectedTransactionType) {
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
        val accountFromId = when (selectedTransactionType) {
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
        val accountToId = when (selectedTransactionType) {
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
        val title = when (selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                enteredTitle
            }
        }
        val transactionForId: Int = when (selectedTransactionType) {
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
        val transactionTimestamp =
            LocalDateTime.of(selectedTransactionDate, selectedTransactionTime).toEpochMilli()

        val accountFrom = if (accountFromId.isNotNull()) {
            selectedAccountFrom
        } else {
            null
        }
        val accountTo = if (accountToId.isNotNull()) {
            selectedAccountTo
        } else {
            null
        }
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = originalTransaction?.id,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = selectedTransactionType,
        )

        myPreferencesRepository.setLastDataChangeTimestamp()
        val id = 0L
        transactionRepository.insertTransaction(
            amountValue = enteredAmountValue,
            accountFrom = accountFrom,
            accountTo = accountTo,
            transaction = transaction,
        )

        // Only for refund transaction
        originalTransaction?.let {
            val refundTransactionIds = originalTransaction.refundTransactionIds?.run {
                originalTransaction.refundTransactionIds?.toMutableList()
            } ?: mutableListOf()
            refundTransactionIds.add(id.toInt())
            updateTransactionUseCase(
                originalTransaction = originalTransaction,
                updatedTransaction = originalTransaction.copy(
                    refundTransactionIds = refundTransactionIds,
                ),
            )
        }
        return id
    }
}

public suspend fun les() {
    val x = withContext(Dispatchers.IO) {

    }
}
