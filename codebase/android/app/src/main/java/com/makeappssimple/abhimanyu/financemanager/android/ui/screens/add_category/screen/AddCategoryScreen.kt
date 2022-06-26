package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.viewmodel.AddCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.viewmodel.AddCategoryScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun AddCategoryScreen(
    screenViewModel: AddCategoryScreenViewModel = hiltViewModel<AddCategoryScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddCategoryScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddCategoryScreenView(
        data = AddCategoryScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberAddCategoryScreenViewState(),
    )
}
