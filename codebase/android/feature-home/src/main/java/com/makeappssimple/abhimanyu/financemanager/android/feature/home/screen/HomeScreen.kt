package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.logError
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
    val homeListItemViewData: List<TransactionData> by screenViewModel.homeListItemViewData
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
        )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    HomeScreenView(
        data = HomeScreenViewData(
            transactionData = homeListItemViewData,
            navigationManager = screenViewModel.navigationManager,
        ),
        state = rememberCommonScreenViewState(),
    )
}
