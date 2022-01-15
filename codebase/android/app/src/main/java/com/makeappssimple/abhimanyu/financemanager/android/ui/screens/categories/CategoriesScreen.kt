package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun CategoriesScreen(
    screenViewModel: CategoriesViewModel = hiltViewModel(),
) {
    logError("Inside CategoriesScreen")

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    CategoriesScreenView(
        data = CategoriesScreenViewData(
            screenViewModel = screenViewModel,
        ),
    )
}
