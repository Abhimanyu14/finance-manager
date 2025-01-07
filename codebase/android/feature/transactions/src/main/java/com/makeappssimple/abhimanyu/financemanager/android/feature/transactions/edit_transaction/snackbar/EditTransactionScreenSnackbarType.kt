package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenSnackbarType

internal sealed class EditTransactionScreenSnackbarType : ScreenSnackbarType {
    data object EditTransactionFailed : EditTransactionScreenSnackbarType()
    data object EditTransactionSuccessful : EditTransactionScreenSnackbarType()
    data object None : EditTransactionScreenSnackbarType()
}
