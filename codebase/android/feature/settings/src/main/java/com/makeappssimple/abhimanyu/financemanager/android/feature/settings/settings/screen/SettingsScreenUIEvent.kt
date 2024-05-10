package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class SettingsScreenUIEvent : ScreenUIEvent {
    public data object OnAccountsListItemClick : SettingsScreenUIEvent()
    public data object OnBackupDataListItemClick : SettingsScreenUIEvent()
    public data object OnCategoriesListItemClick : SettingsScreenUIEvent()
    public data object OnNavigationBackButtonClick : SettingsScreenUIEvent()
    public data object OnOpenSourceLicensesListItemClick : SettingsScreenUIEvent()
    public data object OnRecalculateTotalListItemClick : SettingsScreenUIEvent()
    public data object OnRestoreDataListItemClick : SettingsScreenUIEvent()
    public data object OnToggleReminder : SettingsScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : SettingsScreenUIEvent()
    public data object OnTransactionForListItemClick : SettingsScreenUIEvent()
}
