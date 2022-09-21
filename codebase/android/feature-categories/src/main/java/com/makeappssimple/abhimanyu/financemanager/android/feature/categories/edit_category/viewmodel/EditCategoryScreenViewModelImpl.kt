package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.loadingEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class EditCategoryScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getEmojisUseCase: GetEmojisUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
) : EditCategoryScreenViewModel, ViewModel() {
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )

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

    private val emojis: StateFlow<List<EmojiLocalEntity>> = getEmojisUseCase().defaultListStateIn(
        scope = viewModelScope,
    )
    override val emojiGroups: Flow<Map<String, List<Emoji>>> = combine(
        flow = emojis,
        flow2 = searchText,
    ) { emojis, searchText ->
        emojis.filter { emoji ->
            if (searchText.isBlank()) {
                true
            } else {
                emoji.unicodeName.contains(searchText)
            }
        }.groupBy { emoji ->
            emoji.group
        }.filter { (_, emojis) ->
            emojis.isNotEmpty()
        }
    }.flowOn(
        context = dispatcherProvider.io,
    )

    init {
        savedStateHandle.get<Int>(NavArgs.CATEGORY_ID)?.let { categoryId ->
            getCategory(
                id = categoryId,
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateCategory() {
        val category = category.value ?: return
        val updatedCategory = category
            .copy(
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

    override fun isValidCategoryData(): Boolean {
        return title.value.isNotNullOrBlank() &&
                !isDefaultIncomeCategory(
                    category = title.value,
                ) &&
                !isDefaultExpenseCategory(
                    category = title.value,
                )
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

    private fun getCategory(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            category.update {
                getCategoryUseCase(
                    id = id,
                )
            }
            updateInitialCategoryValue()
        }
    }

    private fun updateInitialCategoryValue() {
        val category = category.value ?: return
        _selectedTransactionTypeIndex.update {
            transactionTypes.indexOf(
                element = category.transactionType,
            )
        }
        _title.value = category.title
        _emoji.value = category.emoji
    }
}
