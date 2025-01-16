package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event

import android.net.Uri
import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateEvents

internal class SettingsScreenUIEventHandler internal constructor(
    private val hasNotificationPermission: Boolean,
    private val uiStateEvents: SettingsScreenUIStateEvents,
    private val createDocument: ((uri: Uri?) -> Unit) -> Unit,
    private val openDocument: () -> Unit,
    private val requestNotificationsPermission: () -> Unit,
) : ScreenUIEventHandler<SettingsScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            is SettingsScreenUIEvent.OnAccountsListItemClick -> {
                uiStateEvents.navigateToAccountsScreen()
            }

            is SettingsScreenUIEvent.OnBackupDataListItemClick -> {
                createDocument {

                }
            }

            is SettingsScreenUIEvent.OnCategoriesListItemClick -> {
                uiStateEvents.navigateToCategoriesScreen()
            }

            is SettingsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick -> {
                uiStateEvents.navigateToOpenSourceLicensesScreen()
            }

            is SettingsScreenUIEvent.OnRecalculateTotalListItemClick -> {
                uiStateEvents.recalculateTotal()
            }

            is SettingsScreenUIEvent.OnRestoreDataListItemClick -> {
                openDocument()
            }

            is SettingsScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }

            is SettingsScreenUIEvent.OnReminderEnabled -> {
                uiStateEvents.disableReminder()
            }

            is SettingsScreenUIEvent.OnReminderDisabled -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                    requestNotificationsPermission()
                } else {
                    uiStateEvents.enableReminder()
                }
            }

            is SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is SettingsScreenUIEvent.OnTransactionForListItemClick -> {
                uiStateEvents.navigateToTransactionForValuesScreen()
            }
        }
    }
}
