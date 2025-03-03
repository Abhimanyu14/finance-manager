package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.usecase.AddCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.AddCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddCategoryScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val addCategoryScreenDataValidationUseCase: AddCategoryScreenDataValidationUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AddCategoryScreenUIStateDelegate by AddCategoryScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    insertCategoriesUseCase = insertCategoriesUseCase,
    navigationKit = navigationKit,
) {
    // region screen args
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private val transactionType: String? = screenArgs.transactionType
    private var categories: ImmutableList<Category> = persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<AddCategoryScreenUIState> =
        MutableStateFlow(
            value = AddCategoryScreenUIState(),
        )
    internal val uiStateEvents: AddCategoryScreenUIStateEvents = AddCategoryScreenUIStateEvents(
        clearSearchText = ::clearSearchText,
        clearTitle = ::clearTitle,
        insertCategory = ::insertCategory,
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        updateEmoji = ::updateEmoji,
        updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        updateSearchText = ::updateSearchText,
        updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
        updateTitle = ::updateTitle,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchCategories()
            transactionType?.let { originalTransactionType ->
                updateSelectedTransactionTypeIndex(
                    updatedSelectedTransactionTypeIndex = validTransactionTypes.indexOf(
                        element = TransactionType.entries.find { transactionType ->
                            transactionType.title == originalTransactionType
                        },
                    ),
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
        categories = getAllCategoriesUseCase()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        observeForRefreshSignal()
    }

    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents()
            }
        }
    }

    private fun updateUiStateAndStateEvents(
        isLoading: Boolean = false,
    ) {
        val validationState = addCategoryScreenDataValidationUseCase(
            categories = categories,
            enteredTitle = title.text.trim(),
        )
        uiState.update {
            AddCategoryScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
                isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                isLoading = isLoading,
                isSupportingTextVisible = validationState.titleError != AddCategoryScreenTitleError.None,
                titleError = validationState.titleError,
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
    // endregion
}
