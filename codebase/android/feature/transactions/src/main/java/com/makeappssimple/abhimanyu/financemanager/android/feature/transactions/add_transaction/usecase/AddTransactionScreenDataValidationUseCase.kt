package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenDataValidationState
import javax.inject.Inject

public class AddTransactionScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        accountFrom: Account?,
        accountTo: Account?,
        maxRefundAmount: Amount?,
        amount: String,
        title: String,
        selectedTransactionType: TransactionType?,
    ): AddTransactionScreenDataValidationState {
        val state = AddTransactionScreenDataValidationState()
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
