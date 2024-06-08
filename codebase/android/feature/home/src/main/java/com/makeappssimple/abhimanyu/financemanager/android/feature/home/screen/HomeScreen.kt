package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModel
import kotlinx.collections.immutable.ImmutableList

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

    // region view model data
    val overviewCardData: OverviewCardViewModelData? by viewModel.overviewCardData.collectAsStateWithLifecycle()
    val homeListItemViewData: ImmutableList<TransactionListItemData> by viewModel.homeListItemViewData.collectAsStateWithLifecycle()
    val isBackupCardVisible: Boolean by viewModel.isBackupCardVisible.collectAsStateWithLifecycle()
    val overviewTabSelectionIndex: Int by viewModel.overviewTabSelectionIndex.collectAsStateWithLifecycle()
    val accountsTotalBalanceAmountValue: Long by viewModel.accountsTotalBalanceAmountValue.collectAsStateWithLifecycle()
    val accountsTotalMinimumBalanceAmountValue: Long by viewModel.accountsTotalMinimumBalanceAmountValue.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberHomeScreenUIStateAndEvents(
        overviewCardData = overviewCardData,
        homeListItemViewData = homeListItemViewData,
        isBackupCardVisible = isBackupCardVisible,
        overviewTabSelectionIndex = overviewTabSelectionIndex,
        accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
        key3 = createDocument,
    ) {
        HomeScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
            createDocument = createDocument,
        )
    }

    HomeScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
