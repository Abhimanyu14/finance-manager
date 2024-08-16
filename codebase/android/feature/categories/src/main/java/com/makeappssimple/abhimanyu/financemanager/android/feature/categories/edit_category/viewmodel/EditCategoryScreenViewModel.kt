package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.EditCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditCategoryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val navigator: Navigator,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
) : ScreenViewModel() {
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
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    private val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    private val transactionType: String? = screenArgs.transactionType
    // endregion

    // region UI state
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    private val emoji: MutableStateFlow<String> = MutableStateFlow(
        value = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES,
    )
    private val selectedTransactionTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = validTransactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    private val screenBottomSheetType: MutableStateFlow<EditCategoryScreenBottomSheetType> =
        MutableStateFlow(
            value = EditCategoryScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<EditCategoryScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = EditCategoryScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            startLoading()
            getAllCategories()
            getOriginalCategory()
            transactionType?.let { originalTransactionType ->
                setSelectedTransactionTypeIndex(
                    validTransactionTypes.indexOf(
                        element = TransactionType.entries.find { transactionType ->
                            transactionType.title == originalTransactionType
                        },
                    )
                )
            }
            completeLoading()
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
                    setSelectedTransactionTypeIndex(
                        validTransactionTypes.indexOf(
                            element = category?.transactionType,
                        )
                    )
                    setTitle(
                        title.value.copy(
                            text = category?.title.orEmpty(),
                            selection = TextRange(category?.title.orEmpty().length),
                        )
                    )
                    setEmoji(category?.emoji.orEmpty())
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

                uiStateAndStateEvents.update {
                    EditCategoryScreenUIStateAndStateEvents(
                        state = EditCategoryScreenUIState(
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
                        ),
                        events = EditCategoryScreenUIStateEvents(
                            clearTitle = ::clearTitle,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setEmoji = ::setEmoji,
                            setTitle = ::setTitle,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setSearchText = ::setSearchText,
                            setSelectedTransactionTypeIndex = ::setSelectedTransactionTypeIndex,
                            updateCategory = ::updateCategory,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun clearTitle() {
        title.update {
            title.value.copy(
                text = "",
            )
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditCategoryScreenBottomSheetType = EditCategoryScreenBottomSheetType.None,
        )
    }

    private fun setEmoji(
        updatedEmoji: String,
    ) {
        emoji.update {
            updatedEmoji
        }
    }

    private fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    private fun setScreenBottomSheetType(
        updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditCategoryScreenBottomSheetType
        }
    }

    private fun setSearchText(
        updatedSearchText: String,
    ) {
        searchText.update {
            updatedSearchText
        }
    }

    private fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    private fun updateCategory() {
        category.value?.copy(
            emoji = emoji.value,
            title = title.value.text,
            transactionType = validTransactionTypes[selectedTransactionTypeIndex.value],
        )?.let { category ->
            viewModelScope.launch {
                updateCategoriesUseCase(category)
                navigator.navigateUp()
            }
        }
    }
    // endregion
}
