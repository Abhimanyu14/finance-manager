package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenSnackbarType

internal sealed class EditAccountScreenSnackbarType : ScreenSnackbarType {
    data object None : EditAccountScreenSnackbarType()
}
