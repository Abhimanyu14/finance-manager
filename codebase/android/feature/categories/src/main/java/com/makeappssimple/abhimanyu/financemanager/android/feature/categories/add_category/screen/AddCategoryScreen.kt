package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel.AddCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun AddCategoryScreen(
    screenViewModel: AddCategoryScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddCategoryScreen",
    )

    // region view model data
    val categories: ImmutableList<Category> by screenViewModel.categories.collectAsStateWithLifecycle()
    val validTransactionTypes: ImmutableList<TransactionType> =
        screenViewModel.validTransactionTypes
    val originalTransactionType: String? = screenViewModel.originalTransactionType
    // endregion

    val uiStateAndStateEvents = rememberAddCategoryScreenUIStateAndEvents(
        categories = categories,
        validTransactionTypes = validTransactionTypes,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndStateEvents,
        key3 = validTransactionTypes,
    ) {
        AddCategoryScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndStateEvents,
            validTransactionTypes = validTransactionTypes,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
        originalTransactionType?.let { originalTransactionType ->
            uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(
                validTransactionTypes.indexOf(
                    element = TransactionType.entries.find { transactionType ->
                        transactionType.title == originalTransactionType
                    },
                )
            )
        }
    }

    AddCategoryScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
