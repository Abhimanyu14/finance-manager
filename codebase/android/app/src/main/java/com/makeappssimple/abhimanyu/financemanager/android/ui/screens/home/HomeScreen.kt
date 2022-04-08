package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    screenViewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(),
) {
    logError(
        message = "Inside HomeScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    HomeScreenView(
        data = HomeScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberHomeScreenViewState(),
    )
}
