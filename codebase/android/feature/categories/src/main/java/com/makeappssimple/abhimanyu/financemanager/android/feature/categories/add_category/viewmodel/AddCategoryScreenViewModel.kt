package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.AddCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddCategoryScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigator: Navigator,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AddCategoryScreenUIStateDelegate by AddCategoryScreenUIStateDelegateImpl(
    insertCategoriesUseCase = insertCategoriesUseCase,
    navigator = navigator,
) {
    // region screen args
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private val transactionType: String? = screenArgs.transactionType
    private val categories: MutableStateFlow<ImmutableList<Category>> = MutableStateFlow(
        persistentListOf()
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<AddCategoryScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddCategoryScreenUIStateAndStateEvents(),
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
            fetchCategories()
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

    // region fetchCategories
    private suspend fun fetchCategories() {
        categories.update {
            getAllCategoriesUseCase()
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
                var titleError: AddCategoryScreenTitleError = AddCategoryScreenTitleError.None
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
                    titleError = AddCategoryScreenTitleError.CategoryExists
                    false
                } else {
                    true
                }

                uiStateAndStateEvents.update {
                    AddCategoryScreenUIStateAndStateEvents(
                        state = AddCategoryScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            isLoading = isLoading,
                            isSupportingTextVisible = titleError != AddCategoryScreenTitleError.None,
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
                        events = AddCategoryScreenUIStateEvents(
                            clearTitle = ::clearTitle,
                            insertCategory = {
                                insertCategory(
                                    coroutineScope = viewModelScope,
                                )
                            },
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
}
