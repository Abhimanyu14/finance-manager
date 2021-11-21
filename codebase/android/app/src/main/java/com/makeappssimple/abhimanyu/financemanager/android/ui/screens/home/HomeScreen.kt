package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    logError("Inside HomeScreen")


    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.trackScreen()
    }

    HomeScreenView()
}
