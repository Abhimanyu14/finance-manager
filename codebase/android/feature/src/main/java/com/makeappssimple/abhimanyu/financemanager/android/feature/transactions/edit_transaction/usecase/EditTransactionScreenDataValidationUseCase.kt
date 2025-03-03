package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenDataValidationState
import javax.inject.Inject

public class EditTransactionScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        accountFrom: Account?,
        accountTo: Account?,
        maxRefundAmount: Amount?,
        amount: String,
        title: String,
        selectedTransactionType: TransactionType?,
    ): EditTransactionScreenDataValidationState {
        val state = EditTransactionScreenDataValidationState()
        if (selectedTransactionType == null) {
            return state
        }
        var amountErrorText: String? = null
        val isCtaButtonEnabled = when (selectedTransactionType) {
            TransactionType.INCOME -> {
                title.isNotNullOrBlank() && amount.toIntOrZero().isNotZero()
            }

            TransactionType.EXPENSE -> {
                title.isNotNullOrBlank() && amount.toIntOrZero().isNotZero()
            }

            TransactionType.TRANSFER -> {
                accountFrom?.id != accountTo?.id && amount.toIntOrZero().isNotZero()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                title.isNotNullOrBlank() && amount.toIntOrZero().isNotZero()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount?.value.orZero()
                val enteredAmountValue = amount.toLongOrZero()
                if (enteredAmountValue > maxRefundAmountValue) {
                    amountErrorText = maxRefundAmount?.toString()
                    false
                } else {
                    amount.toIntOrZero().isNotZero()
                }
            }
        }

        return state
            .copy(
                isCtaButtonEnabled = isCtaButtonEnabled,
                amountErrorText = amountErrorText,
            )
    }
}
