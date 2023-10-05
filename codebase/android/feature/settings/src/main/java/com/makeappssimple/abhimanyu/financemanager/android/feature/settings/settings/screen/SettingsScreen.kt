package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModelImpl

@Composable
fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel<SettingsScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    viewModel.myLogger.logError(
        message = "Inside SettingsScreen",
    )

    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }
    val createDocumentResultLauncher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = CreateJsonDocument(),
        ) { uri ->
            uri?.let {
                isLoading = true
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
                isLoading = true
                viewModel.restoreDataFromDocument(
                    uri = it,
                )
            }
        }
    val notificationsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { _ -> },
    )

    val screenUIData: MyResult<SettingsScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberSettingsScreenUIState(
        data = screenUIData,
    )
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: SettingsScreenUIEvent ->
            when (uiEvent) {
                SettingsScreenUIEvent.BackupData -> {
                    createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
                }

                SettingsScreenUIEvent.RecalculateTotal -> {
                    isLoading = true
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

    SettingsScreenUI(
        uiState = uiState,
        state = rememberCommonScreenUIState(),
        handleUIEvents = handleUIEvents,
    )
}
