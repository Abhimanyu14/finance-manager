package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenSnackbarType

internal sealed class AddAccountScreenSnackbarType : ScreenSnackbarType {
    data object None : AddAccountScreenSnackbarType()
}
