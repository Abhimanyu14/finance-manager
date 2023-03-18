package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModelImpl

@Composable
fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModelImpl>(),
) {
    logError(
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

    val homeListItemViewData: List<TransactionData> by screenViewModel.homeListItemViewData
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
        )
    val showBackupCard: Boolean by screenViewModel.showBackupCard.collectAsStateWithLifecycle(
        initialValue = false,
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    HomeScreenView(
        data = HomeScreenViewData(
            showBackupCard = showBackupCard,
            transactionData = homeListItemViewData,
            createDocument = createDocument,
            navigationManager = screenViewModel.navigationManager,
        ),
        state = rememberCommonScreenViewState(),
    )
}
