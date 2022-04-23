package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.loadingEmoji
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoryScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getEmojisUseCase: GetEmojisUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
) : EditCategoryScreenViewModel, ViewModel() {
    private val _category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    override val category: StateFlow<Category?> = _category

    override val transactionTypes: List<TransactionType> = TransactionType.values()
        .filter {
            it != TransactionType.TRANSFER && it != TransactionType.ADJUSTMENT
        }

    private val _title = MutableStateFlow(
        value = "",
    )
    override val title: StateFlow<String> = _title

    private val _selectedTransactionTypeIndex = MutableStateFlow(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    override val selectedTransactionTypeIndex: StateFlow<Int> = _selectedTransactionTypeIndex

    private val _emoji = MutableStateFlow(
        value = loadingEmoji,
    )
    override val emoji: StateFlow<String> = _emoji

    private val _searchText = MutableStateFlow(
        value = "",
    )
    override val searchText: StateFlow<String> = _searchText

    private val emojis: StateFlow<List<EmojiLocalEntity>> = getEmojisUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )
    override val filteredEmojis: Flow<List<EmojiLocalEntity>> = combine(
        flow = emojis,
        flow2 = searchText,
    ) { emojis, searchText ->
        emojis.filter { emoji ->
            if (searchText.isBlank()) {
                true
            } else {
                emoji.unicodeName.contains(searchText)
            }
        }
    }.flowOn(
        context = dispatcherProvider.io,
    )

    init {
        val categoryId: Int = savedStateHandle.get<Int>(NavArgs.CATEGORY_ID) ?: 0
        getCategory(
            id = categoryId,
        )
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun getCategory(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            _category.value = getCategoryUseCase(
                id = id,
            )
            updateInitialCategoryValue()
        }
    }

    override fun updateCategory() {
        category.value?.let { category ->
            val updatedCategory = category.copy(
                emoji = emoji.value,
                title = title.value,
                transactionType = transactionTypes[selectedTransactionTypeIndex.value],
            )

            viewModelScope.launch(
                context = dispatcherProvider.io,
            ) {
                updateCategoriesUseCase(
                    updatedCategory,
                )
                navigateUp(
                    navigationManager = navigationManager,
                )
            }
        }
    }

    override fun isValidCategoryData(): Boolean {
        return title.value.isNotNullOrBlank()
    }

    override fun clearTitle() {
        _title.value = ""
    }

    override fun updateTitle(
        updatedTitle: String,
    ) {
        _title.value = updatedTitle
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedTransactionTypeIndex.value = updatedIndex
    }

    override fun updateEmoji(
        updatedEmoji: String,
    ) {
        _emoji.value = updatedEmoji
    }

    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        _searchText.value = updatedSearchText
    }

    private fun updateInitialCategoryValue() {
        category.value?.let {
            _selectedTransactionTypeIndex.value = transactionTypes.indexOf(
                element = it.transactionType,
            )
            _title.value = it.title
            _emoji.value = it.emoji
        }
    }
}
