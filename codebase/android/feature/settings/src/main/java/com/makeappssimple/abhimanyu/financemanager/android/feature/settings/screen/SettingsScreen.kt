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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.JSON_MIMETYPE
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getAppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel.SettingsScreenViewModelImpl

@Composable
fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel<SettingsScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
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
    val appVersion = getAppVersion(
        context = context,
    )?.versionName

    SettingsScreenView(
        data = SettingsScreenViewData(
            isLoading = isLoading,
            appVersion = appVersion,
            backupData = {
                createDocumentResultLauncher.launch(JSON_MIMETYPE)
            },
            navigateToTransactionForValuesScreen = screenViewModel::navigateToTransactionForValuesScreen,
            navigateUp = screenViewModel::navigateUp,
            recalculateTotal = {
                isLoading = true
                screenViewModel.recalculateTotal()
            },
            restoreData = {
                openDocumentResultLauncher.launch(arrayOf(JSON_MIMETYPE))
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
