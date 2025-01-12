package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SettingsScreenUIStateDelegateImpl(
    private val alarmKit: AlarmKit,
    private val coroutineScope: CoroutineScope,
    private val navigationKit: NavigationKit,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
) : SettingsScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<SettingsScreenBottomSheetType> =
        MutableStateFlow(
            value = SettingsScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<SettingsScreenSnackbarType> =
        MutableStateFlow(
            value = SettingsScreenSnackbarType.None,
        )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    override fun disableReminder() {
        coroutineScope.launch {
            if (alarmKit.cancelReminderAlarm()) {
                updateScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.CancelReminderSuccessful,
                )
            } else {
                updateScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.CancelReminderFailed,
                )
            }
        }
    }

    override fun enableReminder() {
        coroutineScope.launch {
            alarmKit.scheduleReminderAlarm()
        }
    }

    override fun navigateToAccountsScreen() {
        navigationKit.navigateToAccountsScreen()
    }

    override fun navigateToCategoriesScreen() {
        navigationKit.navigateToCategoriesScreen()
    }

    override fun navigateToOpenSourceLicensesScreen() {
        navigationKit.navigateToOpenSourceLicensesScreen()
    }

    override fun navigateToTransactionForValuesScreen() {
        navigationKit.navigateToTransactionForValuesScreen()
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun recalculateTotal() {
        coroutineScope.launch {
            startLoading()
            recalculateTotalUseCase()
            navigationKit.navigateUp()
        }
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedSettingsScreenBottomSheetType = SettingsScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.None,
        )
    }

    override fun updateScreenBottomSheetType(
        updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedSettingsScreenBottomSheetType
        }
    }

    override fun updateScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedSettingsScreenSnackbarType
        }
    }
    // endregion
}
