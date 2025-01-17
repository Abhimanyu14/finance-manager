package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.usecase.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.EditCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditCategoryScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val editCategoryScreenDataValidationUseCase: EditCategoryScreenDataValidationUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val navigationKit: NavigationKit,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), EditCategoryScreenUIStateDelegate by EditCategoryScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    navigationKit = navigationKit,
    updateCategoriesUseCase = updateCategoriesUseCase,
) {
    // region screen args
    private val screenArgs = EditCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private val categories: MutableStateFlow<ImmutableList<Category>> = MutableStateFlow(
        value = persistentListOf(),
    )
    private val transactionType: String? = screenArgs.transactionType
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<EditCategoryScreenUIState> =
        MutableStateFlow(
            value = EditCategoryScreenUIState(),
        )
    internal val uiStateEvents: EditCategoryScreenUIStateEvents = EditCategoryScreenUIStateEvents(
        clearTitle = ::clearTitle,
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        updateCategory = ::updateCategory,
        updateEmoji = ::updateEmoji,
        updateTitle = ::updateTitle,
        updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        updateSearchText = ::updateSearchText,
        updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                getAllCategories()
                getOriginalCategory()
                transactionType?.let { originalTransactionType ->
                    updateSelectedTransactionTypeIndex(
                        validTransactionTypes.indexOf(
                            element = TransactionType.entries.find { transactionType ->
                                transactionType.title == originalTransactionType
                            },
                        )
                    )
                }
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region getAllCategories
    private fun getAllCategories() {
        viewModelScope.launch {
            categories.update {
                getAllCategoriesUseCase()
            }
        }
    }
    // endregion

    // region getOriginalCategory
    private fun getOriginalCategory() {
        screenArgs.categoryId?.let { id ->
            viewModelScope.launch {
                category.update {
                    getCategoryUseCase(
                        id = id,
                    )
                }

                category.value.let { category ->
                    updateSelectedTransactionTypeIndex(
                        validTransactionTypes.indexOf(
                            element = category?.transactionType,
                        )
                    )
                    updateTitle(
                        title.value.copy(
                            text = category?.title.orEmpty(),
                            selection = TextRange(category?.title.orEmpty().length),
                        )
                    )
                    updateEmoji(category?.emoji.orEmpty())
                }
            }
        }
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                title,
                categories,
                selectedTransactionTypeIndex,
                searchText,
                emoji,
                category,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                        categories,
                        selectedTransactionTypeIndex,
                        searchText,
                        emoji,
                        category,
                    ),
                ->
                val validationState = editCategoryScreenDataValidationUseCase(
                    categories = categories,
                    enteredTitle = title.text.trim(),
                    currentCategory = category,
                )
                var titleError: EditCategoryScreenTitleError = EditCategoryScreenTitleError.None
                val isCtaButtonEnabled = if (title.text.isBlank()) {
                    false
                } else if (isDefaultIncomeCategory(
                        category = title.text.trim(),
                    ) || isDefaultExpenseCategory(
                        category = title.text.trim(),
                    ) || isDefaultInvestmentCategory(
                        category = title.text.trim(),
                    ) || (title.text.trim() != category?.title?.trim() && categories.find {
                        it.title.equalsIgnoringCase(
                            other = title.text.trim(),
                        )
                    }.isNotNull())
                ) {
                    titleError = EditCategoryScreenTitleError.CategoryExists
                    false
                } else {
                    true
                }

                uiState.update {
                    EditCategoryScreenUIState(
                        screenBottomSheetType = screenBottomSheetType,
                        isBottomSheetVisible = screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
                        isCtaButtonEnabled = isCtaButtonEnabled,
                        isLoading = isLoading,
                        isSupportingTextVisible = titleError != EditCategoryScreenTitleError.None,
                        titleError = titleError,
                        selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                        transactionTypesChipUIData = validTransactionTypes.map { transactionType ->
                            ChipUIData(
                                text = transactionType.title,
                            )
                        },
                        emoji = emoji,
                        emojiSearchText = searchText,
                        title = title,
                    )
                }
            }
        }
    }
    // endregion
}
