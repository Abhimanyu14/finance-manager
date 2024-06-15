package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextRange
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.event.EditCategoryScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.rememberEditCategoryScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun EditCategoryScreen(
    screenViewModel: EditCategoryScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditCategoryScreen",
    )

    // region view model data
    val categories: ImmutableList<Category> by screenViewModel.categories.collectAsStateWithLifecycle()
    val category: Category? by screenViewModel.category.collectAsStateWithLifecycle()
    val validTransactionTypes: ImmutableList<TransactionType> =
        screenViewModel.validTransactionTypes
    val originalTransactionType: String? = screenViewModel.originalTransactionType
    // endregion

    val uiStateAndStateEvents = rememberEditCategoryScreenUIStateAndEvents(
        categories = categories,
        category = category,
        validTransactionTypes = validTransactionTypes,
    )
    val screenUIEventHandler = remember(
        screenViewModel,
        uiStateAndStateEvents,
        category,
        validTransactionTypes,
    ) {
        EditCategoryScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndStateEvents,
            category = category,
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

    LaunchedEffect(category) {
        category?.let { category ->
            uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(
                validTransactionTypes.indexOf(
                    element = category.transactionType,
                )
            )
            uiStateAndStateEvents.events.setTitle(
                uiStateAndStateEvents.state.title.copy(
                    text = category.title,
                    selection = TextRange(category.title.length),
                )
            )
            uiStateAndStateEvents.events.setEmoji(category.emoji)
        }
    }

    EditCategoryScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
