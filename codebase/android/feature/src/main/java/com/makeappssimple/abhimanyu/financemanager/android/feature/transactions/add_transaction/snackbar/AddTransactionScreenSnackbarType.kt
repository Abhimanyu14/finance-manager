package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenSnackbarType

internal sealed class AddTransactionScreenSnackbarType : ScreenSnackbarType {
    data object AddTransactionFailed : AddTransactionScreenSnackbarType()
    data object AddTransactionSuccessful : AddTransactionScreenSnackbarType()
    data object None : AddTransactionScreenSnackbarType()
}
