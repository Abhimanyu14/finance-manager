package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModelImpl

@Composable
fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside HomeScreen",
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

    val screenUIData: MyResult<HomeScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    HomeScreenUI(
        events = HomeScreenUIEvents(
            createDocument = createDocument,
            handleOverviewCardAction = screenViewModel::handleOverviewCardAction,
            navigateToAnalysisScreen = screenViewModel::navigateToAnalysisScreen,
            navigateToAddTransactionScreen = screenViewModel::navigateToAddTransactionScreen,
            navigateToSettingsScreen = screenViewModel::navigateToSettingsScreen,
            navigateToAccountsScreen = screenViewModel::navigateToAccountsScreen,
            navigateToTransactionsScreen = screenViewModel::navigateToTransactionsScreen,
            onOverviewTabClick = screenViewModel::setOverviewTabSelectionIndex,
        ),
        uiState = rememberHomeScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
