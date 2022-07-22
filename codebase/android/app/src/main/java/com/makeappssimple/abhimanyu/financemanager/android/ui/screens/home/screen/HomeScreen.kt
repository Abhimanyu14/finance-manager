package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.components.HomeListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.viewmodel.HomeScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.viewmodel.HomeScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError

@Composable
fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModelImpl>(),
) {
    logError(
        message = "Inside HomeScreen",
    )
    val homeListItemViewData: List<HomeListItemViewData> by screenViewModel.homeListItemViewData
        .collectAsState(
            initial = emptyList(),
        )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    HomeScreenView(
        data = HomeScreenViewData(
            homeListItemViewData = homeListItemViewData,
            navigationManager = screenViewModel.navigationManager,
        ),
        state = rememberCommonScreenViewState(),
    )
}
