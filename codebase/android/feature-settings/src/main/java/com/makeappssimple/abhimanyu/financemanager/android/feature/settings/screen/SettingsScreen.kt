package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.util.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel.SettingsScreenViewModelImpl

@Composable
fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel<SettingsScreenViewModelImpl>(),
) {
    logError(
        message = "Inside SettingsScreen",
    )
    val createDocument: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = CreateJsonDocument(),
        ) { uri ->
            uri?.let {
                screenViewModel.backupDataToDocument(
                    uri = it,
                )
            }
        }
    val openDocument: ManagedActivityResultLauncher<Array<String>, Uri?> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri ->
            uri?.let {
                screenViewModel.restoreDataFromDocument(
                    uri = it,
                )
            }
        }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    SettingsScreenView(
        data = SettingsScreenViewData(
            createDocument = createDocument,
            openDocument = openDocument,
            navigationManager = screenViewModel.navigationManager,
            recalculateTotal = {
                screenViewModel.recalculateTotal()
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
