package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterial3Api
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
