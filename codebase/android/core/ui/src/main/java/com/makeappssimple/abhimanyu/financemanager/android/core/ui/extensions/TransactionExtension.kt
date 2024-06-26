package com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

public fun Transaction.getAmountTextColor(): MyColor {
    return this.transactionType.getAmountTextColor(
        isBalanceReduced = this.accountFromId.isNotNull(),
    )
}

private fun TransactionType.getAmountTextColor(
    isBalanceReduced: Boolean,
): MyColor {
    return when (this) {
        TransactionType.INCOME -> {
            MyColor.ON_TERTIARY_CONTAINER
        }

        TransactionType.EXPENSE -> {
            MyColor.ERROR
        }

        TransactionType.TRANSFER -> {
            MyColor.ON_BACKGROUND
        }

        TransactionType.ADJUSTMENT -> {
            if (isBalanceReduced) {
                MyColor.ERROR
            } else {
                MyColor.ON_TERTIARY_CONTAINER
            }
        }

        TransactionType.INVESTMENT -> {
            MyColor.PRIMARY
        }

        TransactionType.REFUND -> {
            MyColor.ON_TERTIARY_CONTAINER
        }
    }
}
