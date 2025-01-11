package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType

@Immutable
internal data class SettingsScreenUIStateEvents(
    val disableReminder: () -> Unit = {},
    val enableReminder: () -> Unit = {},
    val navigateToAccountsScreen: () -> Unit = {},
    val navigateToCategoriesScreen: () -> Unit = {},
    val navigateToOpenSourceLicensesScreen: () -> Unit = {},
    val navigateToTransactionForValuesScreen: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val recalculateTotal: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val resetScreenSnackbarType: () -> Unit = {},
    val setScreenBottomSheetType: (SettingsScreenBottomSheetType) -> Unit = {},
    val setScreenSnackbarType: (SettingsScreenSnackbarType) -> Unit = {},
) : ScreenUIStateEvents
