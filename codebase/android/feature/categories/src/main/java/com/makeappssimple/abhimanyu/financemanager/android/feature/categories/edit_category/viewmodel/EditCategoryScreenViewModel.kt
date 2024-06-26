package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.EditCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
) : ScreenViewModel, ViewModel() {
    private val screenArgs = EditCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private val _categories: MutableStateFlow<ImmutableList<Category>> = MutableStateFlow(
        value = persistentListOf(),
    )
    private val categories: StateFlow<ImmutableList<Category>> = _categories

    private val _category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    private val category: StateFlow<Category?> = _category

    private val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    private val originalTransactionType: String? = screenArgs.originalTransactionType

    // region UI data
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

    internal val uiStateAndStateEvents: MutableStateFlow<EditCategoryScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = EditCategoryScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllCategories()
            getOriginalCategory()

            originalTransactionType?.let { originalTransactionType ->
                setSelectedTransactionTypeIndex(
                    validTransactionTypes.indexOf(
                        element = TransactionType.entries.find { transactionType ->
                            transactionType.title == originalTransactionType
                        },
                    )
                )
            }
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    private fun observeForUiStateAndStateEventsChanges() {
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
                var titleTextFieldErrorTextStringResourceId: Int? = null
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
                    titleTextFieldErrorTextStringResourceId =
                        R.string.screen_add_or_edit_category_error_category_exists
                    false
                } else {
                    true
                }

                uiStateAndStateEvents.update {
                    EditCategoryScreenUIStateAndStateEvents(
                        state = EditCategoryScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
                            isLoading = validTransactionTypes.isEmpty(),
                            isSupportingTextVisible = titleTextFieldErrorTextStringResourceId.isNotNull(),
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                            transactionTypesChipUIData = validTransactionTypes.map { transactionType ->
                                ChipUIData(
                                    text = transactionType.title,
                                )
                            },
                            emoji = emoji,
                            emojiSearchText = searchText,
                            title = title,
                            titleTextFieldErrorTextStringResourceId = titleTextFieldErrorTextStringResourceId,
                            appBarTitleTextStringResourceId = R.string.screen_add_category_appbar_title,
                            ctaButtonLabelTextStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
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

    private fun getAllCategories() {
        viewModelScope.launch {
            _categories.update {
                getAllCategoriesUseCase()
            }
        }
    }

    private fun getOriginalCategory() {
        screenArgs.originalCategoryId?.let { id ->
            viewModelScope.launch {
                _category.update {
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
    // endregion
}
