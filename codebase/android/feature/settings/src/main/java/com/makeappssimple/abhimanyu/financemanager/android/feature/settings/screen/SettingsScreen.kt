package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel.SettingsScreenViewModelImpl

@Composable
fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel<SettingsScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside SettingsScreen",
    )
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

    val screenUIData: SettingsScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    SettingsScreenUI(
        data = screenUIData?.copy(
            isLoading = isLoading,
        ) ?: SettingsScreenUIData(),
        events = SettingsScreenUIEvents(
            backupData = {
                createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
            },
            navigateToTransactionForValuesScreen = screenViewModel::navigateToTransactionForValuesScreen,
            navigateUp = screenViewModel::navigateUp,
            recalculateTotal = {
                isLoading = true
                screenViewModel.recalculateTotal()
            },
            restoreData = {
                openDocumentResultLauncher.launch(arrayOf(MimeTypeConstants.JSON))
            },
        ),
        state = rememberCommonScreenUIState(),
    )
}
