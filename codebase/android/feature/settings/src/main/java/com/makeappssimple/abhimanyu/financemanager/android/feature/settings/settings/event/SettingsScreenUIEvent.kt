package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class SettingsScreenUIEvent : ScreenUIEvent {
    data object OnAccountsListItemClick : SettingsScreenUIEvent()
    data object OnBackupDataListItemClick : SettingsScreenUIEvent()
    data object OnCategoriesListItemClick : SettingsScreenUIEvent()
    data object OnNavigationBackButtonClick : SettingsScreenUIEvent()
    data object OnOpenSourceLicensesListItemClick : SettingsScreenUIEvent()
    data object OnRecalculateTotalListItemClick : SettingsScreenUIEvent()
    data object OnReminderEnabled : SettingsScreenUIEvent()
    data object OnReminderDisabled : SettingsScreenUIEvent()
    data object OnRestoreDataListItemClick : SettingsScreenUIEvent()
    data object OnSnackbarDismissed : SettingsScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : SettingsScreenUIEvent()
    data object OnTransactionForListItemClick : SettingsScreenUIEvent()
}
