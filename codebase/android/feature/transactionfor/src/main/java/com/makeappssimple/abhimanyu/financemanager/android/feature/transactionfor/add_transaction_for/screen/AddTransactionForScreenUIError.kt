package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.annotation.StringRes
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

public enum class AddTransactionForScreenUIError(
    @StringRes public val textStringResourceId: Int,
) {
    EXISTS(
        textStringResourceId = R.string.screen_add_or_edit_transaction_for_error_exists,
    ),
}
