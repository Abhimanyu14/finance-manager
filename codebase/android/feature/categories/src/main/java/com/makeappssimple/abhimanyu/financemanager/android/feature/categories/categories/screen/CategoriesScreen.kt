package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

@Composable
public fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside CategoriesScreen",
    )

    val categoriesGridItemDataMap: ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>
            by viewModel.categoriesGridItemDataMap.collectAsStateWithLifecycle()

    val uiStateAndEvents = rememberCategoriesScreenUIStateAndEvents(
        categoriesGridItemDataMap = categoriesGridItemDataMap,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        CategoriesScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    CategoriesScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
