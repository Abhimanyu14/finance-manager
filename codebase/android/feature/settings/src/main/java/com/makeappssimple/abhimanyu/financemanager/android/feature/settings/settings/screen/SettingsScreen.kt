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
    screenViewModel.myLogger.logError(
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
                isLoading = true
                screenViewModel.restoreDataFromDocument(
                    uri = it,
                )
            }
        }
    val notificationsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { _ -> },
    )

    val screenUIData: MyResult<SettingsScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberSettingsScreenUIState(
        data = screenUIData,
    )

    SettingsScreenUI(
        events = SettingsScreenUIEvents(
            backupData = {
                createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
            },
            navigateToCategoriesScreen = screenViewModel::navigateToCategoriesScreen,
            navigateToAccountsScreen = screenViewModel::navigateToAccountsScreen,
            navigateToOpenSourceLicensesScreen = screenViewModel::navigateToOpenSourceLicensesScreen,
            navigateToTransactionForValuesScreen = screenViewModel::navigateToTransactionForValuesScreen,
            navigateUp = screenViewModel::navigateUp,
            recalculateTotal = {
                isLoading = true
                screenViewModel.recalculateTotal()
            },
            restoreData = {
                openDocumentResultLauncher.launch(arrayOf(MimeTypeConstants.JSON))
            },
            toggleReminder = {
                if (uiState.isReminderEnabled.orFalse()) {
                    screenViewModel.disableReminder()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hasNotificationPermission = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                        if (!hasNotificationPermission) {
                            notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            screenViewModel.enableReminder()
                        }
                    } else {
                        screenViewModel.enableReminder()
                    }
                }
            },
        ),
        uiState = uiState,
        state = rememberCommonScreenUIState(),
    )
}
