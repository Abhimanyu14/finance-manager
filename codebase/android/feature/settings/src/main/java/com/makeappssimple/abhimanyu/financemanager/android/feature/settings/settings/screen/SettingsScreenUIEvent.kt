package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class SettingsScreenUIEvent : ScreenUIEvent {
    public data object BackupData : SettingsScreenUIEvent()
    public data object NavigateToCategoriesScreen : SettingsScreenUIEvent()
    public data object NavigateToAccountsScreen : SettingsScreenUIEvent()
    public data object NavigateToOpenSourceLicensesScreen : SettingsScreenUIEvent()
    public data object NavigateToTransactionForValuesScreen : SettingsScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : SettingsScreenUIEvent()
    public data object RecalculateTotal : SettingsScreenUIEvent()
    public data object RestoreData : SettingsScreenUIEvent()
    public data object ToggleReminder : SettingsScreenUIEvent()
}
