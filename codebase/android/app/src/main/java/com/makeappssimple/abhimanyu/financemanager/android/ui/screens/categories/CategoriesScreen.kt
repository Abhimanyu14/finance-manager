package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterialApi
@Composable
fun CategoriesScreen(
    screenViewModel: CategoriesViewModel = hiltViewModel(),
) {
    logError(
        message = "Inside CategoriesScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    CategoriesScreenView(
        data = CategoriesScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberCategoriesScreenViewState(),
    )
}
