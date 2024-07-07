package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

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
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.AddCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddCategoryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // region initial data
    private val originalTransactionType: String? = screenArgs.originalTransactionType
    private val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    private val categories: MutableStateFlow<ImmutableList<Category>> = MutableStateFlow(
        persistentListOf()
    )
    // endregion

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
    private val screenBottomSheetType: MutableStateFlow<AddCategoryScreenBottomSheetType> =
        MutableStateFlow(
            value = AddCategoryScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<AddCategoryScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddCategoryScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchCategories()
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

    // region fetchCategories
    private suspend fun fetchCategories() {
        categories.update {
            getAllCategoriesUseCase()
        }
    }
    // endregion

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    // region observeForUiStateAndStateEventsChanges
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
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                        categories,
                        selectedTransactionTypeIndex,
                        searchText,
                        emoji,
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
                    ) || categories.find {
                        it.title.equalsIgnoringCase(
                            other = title.text.trim(),
                        )
                    }.isNotNull()
                ) {
                    titleTextFieldErrorTextStringResourceId =
                        R.string.screen_add_or_edit_category_error_category_exists
                    false
                } else {
                    true
                }

                uiStateAndStateEvents.update {
                    AddCategoryScreenUIStateAndStateEvents(
                        state = AddCategoryScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
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
                        events = AddCategoryScreenUIStateEvents(
                            clearTitle = ::clearTitle,
                            insertCategory = ::insertCategory,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setEmoji = ::setEmoji,
                            setTitle = ::setTitle,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setSearchText = ::setSearchText,
                            setSelectedTransactionTypeIndex = ::setSelectedTransactionTypeIndex,
                        ),
                    )
                }
            }
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

    private fun insertCategory() {
        val category = Category(
            emoji = emoji.value,
            title = title.value.text,
            transactionType = validTransactionTypes[selectedTransactionTypeIndex.value],
        )
        viewModelScope.launch {
            insertCategoriesUseCase(category)
            navigator.navigateUp()
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
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
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddCategoryScreenBottomSheetType
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
