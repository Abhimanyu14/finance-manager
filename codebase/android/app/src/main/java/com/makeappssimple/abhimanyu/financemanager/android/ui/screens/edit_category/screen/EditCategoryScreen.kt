package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.viewmodel.EditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.viewmodel.EditCategoryScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun EditCategoryScreen(
    screenViewModel: EditCategoryScreenViewModel = hiltViewModel<EditCategoryScreenViewModelImpl>(),
    categoryId: Int?,
) {
    logError(
        message = "Inside EditCategoryScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    EditCategoryScreenView(
        data = EditCategoryScreenViewData(
            screenViewModel = screenViewModel,
            categoryId = categoryId,
        ),
        state = rememberEditCategoryScreenViewState(),
    )
}
