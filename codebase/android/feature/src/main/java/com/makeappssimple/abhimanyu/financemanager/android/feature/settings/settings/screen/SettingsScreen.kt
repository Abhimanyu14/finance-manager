package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalLogKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event.SettingsScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel

@Composable
public fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalLogKit.current
    myLogger.logInfo(
        message = "Inside SettingsScreen",
    )

    val context = LocalContext.current

    val uiState: SettingsScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: SettingsScreenUIStateEvents = screenViewModel.uiStateEvents

    val onDocumentCreated: (Uri?) -> Unit = { uri: Uri? ->
        uri?.let {
            screenViewModel.backupDataToDocument(
                uri = uri,
            )
        }
    }
    val createDocumentResultLauncher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = CreateJsonDocument(),
            onResult = onDocumentCreated,
        )
    val onDocumentOpened = { uri: Uri ->
        screenViewModel.restoreDataFromDocument(
            uri = uri,
        )
    }
    val openDocumentResultLauncher: ManagedActivityResultLauncher<Array<String>, Uri?> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let {
                onDocumentOpened(it)
            }
        }
    val onNotificationPermissionRequestResult = { isPermissionGranted: Boolean ->
        if (isPermissionGranted) {
            uiStateEvents.enableReminder()
        }
    }
    val notificationsPermissionLauncher: ManagedActivityResultLauncher<String, Boolean> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = onNotificationPermissionRequestResult,
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

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        SettingsScreenUIEventHandler(
            hasNotificationPermission = hasNotificationPermission,
            uiStateEvents = uiStateEvents,
            createDocument = { handler: (uri: Uri?) -> Unit ->
                createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
            },
            openDocument = {
                openDocumentResultLauncher.launch(arrayOf(MimeTypeConstants.JSON))
            },
            requestNotificationsPermission = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            },
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    SettingsScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
