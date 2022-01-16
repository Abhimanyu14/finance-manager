package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun AddCategoryScreen(
    screenViewModel: AddCategoryViewModel = hiltViewModel(),
) {
    logError("Inside AddCategoryScreen")

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddCategoryScreenView(
        data = AddCategoryScreenViewData(
            screenViewModel = screenViewModel,
        ),
    )
}
