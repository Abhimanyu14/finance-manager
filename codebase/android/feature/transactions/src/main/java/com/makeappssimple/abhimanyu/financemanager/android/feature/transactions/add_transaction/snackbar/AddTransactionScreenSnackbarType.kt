package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenSnackbarType

internal sealed class AddTransactionScreenSnackbarType : ScreenSnackbarType {
    data object None : AddTransactionScreenSnackbarType()
}
