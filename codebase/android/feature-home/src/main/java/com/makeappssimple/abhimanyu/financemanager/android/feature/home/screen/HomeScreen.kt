package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModelImpl

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
