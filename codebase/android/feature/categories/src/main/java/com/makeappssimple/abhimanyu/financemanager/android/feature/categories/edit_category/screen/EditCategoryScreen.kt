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
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun EditCategoryScreen(
    screenViewModel: EditCategoryScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditCategoryScreen",
    )

    // region view model data
    val categories: ImmutableList<Category> by viewModel.categories.collectAsStateWithLifecycle()
    val category: Category? by viewModel.category.collectAsStateWithLifecycle()
    val validTransactionTypes = viewModel.validTransactionTypes
    val originalTransactionType: String? = viewModel.originalTransactionType
    // endregion

    val uiStateAndEvents = rememberEditCategoryScreenUIStateAndEvents(
        categories = categories,
        category = category,
        validTransactionTypes = validTransactionTypes,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: EditCategoryScreenUIEvent ->
            when (uiEvent) {
                is EditCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setSearchText("")
                }

                is EditCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is EditCategoryScreenUIEvent.OnCtaButtonClick -> {
                    category?.let { category ->
                        uiStateAndEvents.state.selectedTransactionTypeIndex?.let { selectedTransactionTypeIndex ->
                            viewModel.updateCategory(
                                category = category.copy(
                                    emoji = uiStateAndEvents.state.emoji,
                                    title = uiStateAndEvents.state.title.text,
                                    transactionType = validTransactionTypes[selectedTransactionTypeIndex],
                                ),
                            )
                        }
                    }
                    Unit
                }

                is EditCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                    uiStateAndEvents.events.clearTitle()
                }

                is EditCategoryScreenUIEvent.OnEmojiCircleClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        EditCategoryScreenBottomSheetType.SelectEmoji
                    )
                }

                is EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is EditCategoryScreenUIEvent.OnEmojiUpdated -> {
                    uiStateAndEvents.events.setEmoji(uiEvent.updatedEmoji)
                }

                is EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                    uiStateAndEvents.events.setSearchText(uiEvent.updatedSearchText)
                }

                is EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    uiStateAndEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
                }

                is EditCategoryScreenUIEvent.OnTitleUpdated -> {
                    uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
        originalTransactionType?.let { originalTransactionType ->
            uiStateAndEvents.events.setSelectedTransactionTypeIndex(
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
            uiStateAndEvents.events.setSelectedTransactionTypeIndex(
                validTransactionTypes.indexOf(
                    element = category.transactionType,
                )
            )
            uiStateAndEvents.events.setTitle(
                uiStateAndEvents.state.title.copy(
                    text = category.title,
                    selection = TextRange(category.title.length),
                )
            )
            uiStateAndEvents.events.setEmoji(category.emoji)
        }
    }

    EditCategoryScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
