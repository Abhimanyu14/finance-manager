package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class SettingsScreenUIEvent : ScreenUIEvent {
    data object BackupData : SettingsScreenUIEvent()
    data object NavigateToCategoriesScreen : SettingsScreenUIEvent()
    data object NavigateToAccountsScreen : SettingsScreenUIEvent()
    data object NavigateToOpenSourceLicensesScreen : SettingsScreenUIEvent()
    data object NavigateToTransactionForValuesScreen : SettingsScreenUIEvent()
    data object NavigateUp : SettingsScreenUIEvent()
    data object RecalculateTotal : SettingsScreenUIEvent()
    data object RestoreData : SettingsScreenUIEvent()
    data object ToggleReminder : SettingsScreenUIEvent()
}
