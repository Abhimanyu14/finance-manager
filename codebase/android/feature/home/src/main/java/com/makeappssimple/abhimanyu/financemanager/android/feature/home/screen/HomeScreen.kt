package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModelImpl

@Composable
fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside HomeScreen",
    )
    val createDocument: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = CreateJsonDocument(),
        ) { uri ->
            uri?.let {
                viewModel.backupDataToDocument(
                    uri = it,
                )
            }
        }

    val screenUIData: MyResult<HomeScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: HomeScreenUIEvent ->
            when (uiEvent) {
                is HomeScreenUIEvent.CreateDocument -> {
                    createDocument.launch(MimeTypeConstants.JSON)
                }

                else -> {
                    viewModel.handleUIEvents(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    HomeScreenUI(
        uiState = rememberHomeScreenUIState(
            data = screenUIData,
        ),
        handleUIEvents = handleUIEvents,
    )
}
