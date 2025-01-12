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

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
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

    fun updateScreenBottomSheetType(
        updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType,
    )

    fun updateScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
    )
    // endregion
}
