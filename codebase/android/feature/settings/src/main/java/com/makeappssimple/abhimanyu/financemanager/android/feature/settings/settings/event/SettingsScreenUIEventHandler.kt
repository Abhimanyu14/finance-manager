package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event

import android.net.Uri
import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateAndStateEvents

public class SettingsScreenUIEventHandler internal constructor(
    private val hasNotificationPermission: Boolean,
    private val uiStateAndStateEvents: SettingsScreenUIStateAndStateEvents,
    private val createDocument: ((uri: Uri?) -> Unit) -> Unit,
    private val openDocument: () -> Unit,
    private val requestNotificationsPermission: () -> Unit,
) {

    public fun handleUIEvent(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            is SettingsScreenUIEvent.OnBackupDataListItemClick -> {
                createDocument {

                }
            }

            is SettingsScreenUIEvent.OnRecalculateTotalListItemClick -> {
                uiStateAndStateEvents.events.recalculateTotal()
            }

            is SettingsScreenUIEvent.OnRestoreDataListItemClick -> {
                openDocument()
            }

            is SettingsScreenUIEvent.OnToggleReminder -> {
                if (uiStateAndStateEvents.state.isReminderEnabled.orFalse()) {
                    uiStateAndStateEvents.events.disableReminder()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                        requestNotificationsPermission()
                    } else {
                        uiStateAndStateEvents.events.enableReminder()
                    }
                }
            }

            is SettingsScreenUIEvent.OnAccountsListItemClick -> {
                uiStateAndStateEvents.events.navigateToAccountsScreen()
            }

            is SettingsScreenUIEvent.OnCategoriesListItemClick -> {
                uiStateAndStateEvents.events.navigateToCategoriesScreen()
            }

            is SettingsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick -> {
                uiStateAndStateEvents.events.navigateToOpenSourceLicensesScreen()
            }

            is SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is SettingsScreenUIEvent.OnTransactionForListItemClick -> {
                uiStateAndStateEvents.events.navigateToTransactionForValuesScreen()
            }
        }
    }
}
