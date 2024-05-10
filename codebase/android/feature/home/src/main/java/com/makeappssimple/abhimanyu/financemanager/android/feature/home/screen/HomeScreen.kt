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

@Composable
public fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel(),
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
    val uiState = rememberHomeScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
        key3 = createDocument,
    ) {
        { uiEvent: HomeScreenUIEvent ->
            when (uiEvent) {
                is HomeScreenUIEvent.OnBackupCardClick -> {
                    createDocument.launch(MimeTypeConstants.JSON)
                }

                is HomeScreenUIEvent.OnTotalBalanceCardClick -> {
                    viewModel.navigateToAccountsScreen()
                }

                is HomeScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddTransactionScreen()
                }

                is HomeScreenUIEvent.OnOverviewCard.Click -> {
                    viewModel.navigateToAnalysisScreen()
                }

                is HomeScreenUIEvent.OnTopAppBarSettingsButtonClick -> {
                    viewModel.navigateToSettingsScreen()
                }

                is HomeScreenUIEvent.OnHomeRecentTransactionsClick -> {
                    viewModel.navigateToTransactionsScreen()
                }

                is HomeScreenUIEvent.OnTransactionListItemClick -> {
                    viewModel.navigateToViewTransactionScreen(
                        transactionId = uiEvent.transactionId,
                    )
                }

                is HomeScreenUIEvent.OnOverviewCard.Action -> {
                    viewModel.handleOverviewCardAction(
                        overviewCardAction = uiEvent.overviewCardAction,
                    )
                }

                is HomeScreenUIEvent.OnOverviewCard.TabClick -> {
                    viewModel.setOverviewTabSelectionIndex(
                        updatedOverviewTabSelectionIndex = uiEvent.index,
                    )
                }
            }
        }
    }

    HomeScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
