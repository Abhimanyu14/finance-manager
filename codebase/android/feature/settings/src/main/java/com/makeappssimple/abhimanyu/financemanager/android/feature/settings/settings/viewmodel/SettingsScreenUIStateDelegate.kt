package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface SettingsScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<SettingsScreenBottomSheetType>
    val screenSnackbarType: MutableStateFlow<SettingsScreenSnackbarType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun disableReminder()

    fun enableReminder()

    fun navigateToAccountsScreen()

    fun navigateToCategoriesScreen()

    fun navigateToOpenSourceLicensesScreen()

    fun navigateToTransactionForValuesScreen()

    fun navigateUp()

    fun recalculateTotal()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun setScreenBottomSheetType(
        updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType,
    )

    fun setScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
    )
    // endregion
}
