package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModelImpl

@Composable
public fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel<CategoriesScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside CategoriesScreen",
    )

    val screenUIData: MyResult<CategoriesScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberCategoriesScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: CategoriesScreenUIEvent ->
            @Suppress("UNUSED_EXPRESSION")
            when (uiEvent) {
                else -> {
                    viewModel.handleUIEvent(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    CategoriesScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
