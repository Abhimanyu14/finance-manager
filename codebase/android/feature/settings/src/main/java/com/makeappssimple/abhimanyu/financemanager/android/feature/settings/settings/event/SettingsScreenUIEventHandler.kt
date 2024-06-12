package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel

public class SettingsScreenUIEventHandler internal constructor(
    private val viewModel: SettingsScreenViewModel,
    private val uiStateAndEvents: SettingsScreenUIStateAndStateEvents,
    private val hasNotificationPermission: Boolean,
    private val createDocumentResultLauncher: ManagedActivityResultLauncher<String, Uri?>,
    private val notificationsPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    private val openDocumentResultLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>,
) {
    public fun handleUIEvent(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            is SettingsScreenUIEvent.OnBackupDataListItemClick -> {
                createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
            }

            is SettingsScreenUIEvent.OnRecalculateTotalListItemClick -> {
                viewModel.recalculateTotal()
            }

            is SettingsScreenUIEvent.OnRestoreDataListItemClick -> {
                openDocumentResultLauncher.launch(arrayOf(MimeTypeConstants.JSON))
            }

            is SettingsScreenUIEvent.OnToggleReminder -> {
                if (uiStateAndEvents.state.isReminderEnabled.orFalse()) {
                    viewModel.disableReminder()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                        notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        viewModel.enableReminder()
                    }
                }
            }

            is SettingsScreenUIEvent.OnAccountsListItemClick -> {
                viewModel.navigateToAccountsScreen()
            }

            is SettingsScreenUIEvent.OnCategoriesListItemClick -> {
                viewModel.navigateToCategoriesScreen()
            }

            is SettingsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick -> {
                viewModel.navigateToOpenSourceLicensesScreen()
            }

            is SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is SettingsScreenUIEvent.OnTransactionForListItemClick -> {
                viewModel.navigateToTransactionForValuesScreen()
            }
        }
    }
}
