package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event.SettingsScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.rememberSettingsScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel
import kotlinx.coroutines.launch

@Composable
public fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside SettingsScreen",
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val createDocumentResultLauncher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = CreateJsonDocument(),
        ) { uri ->
            uri?.let {
                screenViewModel.backupDataToDocument(
                    uri = it,
                )
            }
        }
    val openDocumentResultLauncher: ManagedActivityResultLauncher<Array<String>, Uri?> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let {
                screenViewModel.restoreDataFromDocument(
                    uri = it,
                )
            }
        }
    val notificationsPermissionLauncher: ManagedActivityResultLauncher<String, Boolean> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isPermissionGranted ->
                if (isPermissionGranted) {
                    screenViewModel.enableReminder()
                }
            },
        )
    val hasNotificationPermission: Boolean = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    // region view model data
    val reminder: Reminder? by screenViewModel.reminder.collectAsStateWithLifecycle()
    val isLoading: Boolean by screenViewModel.isLoading.collectAsStateWithLifecycle()
    val appVersionName: String = screenViewModel.appVersionName
    // endregion

    val uiStateAndEvents = rememberSettingsScreenUIStateAndEvents(
        isLoading = isLoading,
        reminder = reminder,
        appVersionName = appVersionName,
    )
    val screenUIEventHandler = remember(
        screenViewModel,
        uiStateAndEvents,
        hasNotificationPermission,
        createDocumentResultLauncher,
        openDocumentResultLauncher,
        notificationsPermissionLauncher,
    ) {
        SettingsScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndEvents = uiStateAndEvents,
            hasNotificationPermission = hasNotificationPermission,
            createDocumentResultLauncher = createDocumentResultLauncher,
            notificationsPermissionLauncher = notificationsPermissionLauncher,
            openDocumentResultLauncher = openDocumentResultLauncher,
        )
    }
    val restoreErrorMessage = stringResource(
        id = R.string.screen_settings_restore_error_message,
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.event.collect { event ->
            when (event) {
                is SettingsScreenEvent.RestoreDataFailed -> {
                    coroutineScope.launch {
                        val result = uiStateAndEvents.state.snackbarHostState
                            .showSnackbar(
                                message = restoreErrorMessage,
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {}
                            SnackbarResult.Dismissed -> {}
                        }
                    }
                }
            }
        }
    }

    SettingsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
