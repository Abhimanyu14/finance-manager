package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SettingsScreenUIStateDelegateImpl(
    private val alarmKit: AlarmKit,
    private val coroutineScope: CoroutineScope,
    private val navigator: Navigator,
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
    // endregion

    // region state events
    override fun disableReminder() {
        coroutineScope.launch {
            if (alarmKit.cancelReminderAlarm()) {
                setScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.CancelReminderSuccessful,
                )
            } else {
                setScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.CancelReminderFailed,
                )
            }
        }
    }

    override fun enableReminder() {
        coroutineScope.launch {
            alarmKit.setReminderAlarm()
        }
    }

    override fun navigateToAccountsScreen() {
        navigator.navigateToAccountsScreen()
    }

    override fun navigateToCategoriesScreen() {
        navigator.navigateToCategoriesScreen()
    }

    override fun navigateToOpenSourceLicensesScreen() {
        navigator.navigateToOpenSourceLicensesScreen()
    }

    override fun navigateToTransactionForValuesScreen() {
        navigator.navigateToTransactionForValuesScreen()
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun recalculateTotal() {
        coroutineScope.launch {
            startLoading()
            recalculateTotalUseCase()
            navigator.navigateUp()
        }
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedSettingsScreenBottomSheetType = SettingsScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedSettingsScreenBottomSheetType
        }
    }

    override fun setScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedSettingsScreenSnackbarType
        }
    }
    // endregion
}
