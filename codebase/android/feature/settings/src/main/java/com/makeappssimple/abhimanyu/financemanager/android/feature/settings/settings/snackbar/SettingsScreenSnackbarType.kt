package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenSnackbarType

internal sealed class SettingsScreenSnackbarType : ScreenSnackbarType {
    data object None : SettingsScreenSnackbarType()
    data object RestoreDataFailed : SettingsScreenSnackbarType()
    data object CancelReminderSuccessful : SettingsScreenSnackbarType()
    data object CancelReminderFailed : SettingsScreenSnackbarType()
}
