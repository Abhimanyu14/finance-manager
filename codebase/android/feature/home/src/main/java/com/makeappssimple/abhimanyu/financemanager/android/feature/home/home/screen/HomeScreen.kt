package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.event.HomeScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.viewmodel.HomeScreenViewModel

@Composable
public fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logInfo(
        message = "Inside HomeScreen",
    )
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

    val uiState: HomeScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: HomeScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        HomeScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
            createDocument = { handler: (uri: Uri?) -> Unit ->
                createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
            },
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    HomeScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
