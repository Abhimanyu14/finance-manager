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
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddCategoryScreen",
    )

    // region view model data
    val categories: ImmutableList<Category> by viewModel.categories.collectAsStateWithLifecycle()
    val validTransactionTypes: ImmutableList<TransactionType> = viewModel.validTransactionTypes
    val originalTransactionType: String? = viewModel.originalTransactionType
    // endregion

    val uiStateAndEvents = rememberAddCategoryScreenUIStateAndEvents(
        categories = categories,
        validTransactionTypes = validTransactionTypes,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddCategoryScreenUIEvent ->
            when (uiEvent) {
                is AddCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setSearchText("")
                }

                is AddCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddCategoryScreenUIEvent.OnCtaButtonClick -> {
                    uiStateAndEvents.state.selectedTransactionTypeIndex?.let { selectedTransactionTypeIndex ->
                        val transactionType = validTransactionTypes[selectedTransactionTypeIndex]
                        viewModel.insertCategory(
                            category = Category(
                                emoji = uiStateAndEvents.state.emoji,
                                title = uiStateAndEvents.state.title.text,
                                transactionType = transactionType,
                            ),
                        )
                    }
                    Unit
                }

                is AddCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                    uiStateAndEvents.events.clearTitle()
                }

                is AddCategoryScreenUIEvent.OnEmojiCircleClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddCategoryScreenBottomSheetType.SelectEmoji
                    )
                }

                is AddCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddCategoryScreenUIEvent.OnEmojiUpdated -> {
                    uiStateAndEvents.events.setEmoji(uiEvent.updatedEmoji)
                }

                is AddCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                    uiStateAndEvents.events.setSearchText(uiEvent.updatedSearchText)
                }

                is AddCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    uiStateAndEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
                }

                is AddCategoryScreenUIEvent.OnTitleUpdated -> {
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

    AddCategoryScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
