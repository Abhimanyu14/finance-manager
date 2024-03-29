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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModelImpl
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel<SettingsScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
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
                viewModel.backupDataToDocument(
                    uri = it,
                )
            }
        }
    val openDocumentResultLauncher: ManagedActivityResultLauncher<Array<String>, Uri?> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let {
                viewModel.restoreDataFromDocument(
                    uri = it,
                )
            }
        }
    val notificationsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isPermissionGranted ->
            if (isPermissionGranted) {
                viewModel.enableReminder()
            }
        },
    )

    val screenUIData: MyResult<SettingsScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberSettingsScreenUIState(
        data = screenUIData,
    )
    val handleUIEvents = remember(
        viewModel,
        uiState,
        createDocumentResultLauncher,
        openDocumentResultLauncher,
        notificationsPermissionLauncher,
    ) {
        { uiEvent: SettingsScreenUIEvent ->
            when (uiEvent) {
                SettingsScreenUIEvent.BackupData -> {
                    createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
                }

                SettingsScreenUIEvent.RecalculateTotal -> {
                    viewModel.recalculateTotal()
                }

                SettingsScreenUIEvent.RestoreData -> {
                    openDocumentResultLauncher.launch(arrayOf(MimeTypeConstants.JSON))
                }

                SettingsScreenUIEvent.ToggleReminder -> {
                    if (uiState.isReminderEnabled.orFalse()) {
                        viewModel.disableReminder()
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val hasNotificationPermission = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                            if (!hasNotificationPermission) {
                                notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                viewModel.enableReminder()
                            }
                        } else {
                            viewModel.enableReminder()
                        }
                    }
                }

                else -> {
                    viewModel.handleUIEvents(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }
    val restoreErrorMessage = stringResource(
        id = R.string.screen_settings_restore_error_message,
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.event.collect {
            when (it) {
                SettingsScreenEvent.RestoreDataFailed -> {
                    coroutineScope.launch {
                        val result = uiState.snackbarHostState
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
        uiState = uiState,
        handleUIEvents = handleUIEvents,
    )
}
