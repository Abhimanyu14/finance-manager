package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.loadingEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditCategoryScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getEmojisUseCase: GetEmojisUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
) : AddOrEditCategoryScreenViewModel, ViewModel() {
    private val categories: StateFlow<List<Category>> = getCategoriesUseCase().defaultListStateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
    )
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )

    override val transactionTypes: List<TransactionType> = TransactionType.values()
        .filter {
            it != TransactionType.TRANSFER && it != TransactionType.ADJUSTMENT && it != TransactionType.REFUND
        }

    private val _title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    override val title: StateFlow<TextFieldValue> = _title

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
        savedStateHandle.get<String>(NavArgs.TRANSACTION_TYPE)?.let { transactionType ->
            updateSelectedTransactionTypeIndex(
                updatedIndex = transactionTypes.indexOf(
                    element = TransactionType.values().find {
                        it.title == transactionType
                    },
                )
            )
        }
        savedStateHandle.get<Int>(NavArgs.CATEGORY_ID)?.let { categoryId ->
            getCategory(
                id = categoryId,
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun insertCategory() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertCategoriesUseCase(
                Category(
                    emoji = emoji.value,
                    title = title.value.text,
                    transactionType = transactionTypes[selectedTransactionTypeIndex.value],
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun updateCategory() {
        val updatedCategory = category.value?.copy(
            emoji = emoji.value,
            title = title.value.text,
            transactionType = transactionTypes[selectedTransactionTypeIndex.value],
        ) ?: return
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
        val title = title.value.text

        // TODO-Abhi: Error message - "Title can not be empty"
        if (title.isBlank()) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (isDefaultIncomeCategory(
                category = title,
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (isDefaultExpenseCategory(
                category = title,
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (isDefaultInvestmentCategory(
                category = title,
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (title != category.value?.title &&
            categories.value.find {
                it.title.equalsIgnoringCase(
                    other = title,
                )
            }.isNotNull()
        ) {
            return false
        }
        return true
    }

    override fun clearTitle() {
        updateTitle(
            updatedTitle = title.value.copy(
                text = "",
            ),
        )
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        _title.update {
            updatedTitle
        }
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
        updateSelectedTransactionTypeIndex(
            updatedIndex = transactionTypes.indexOf(
                element = category.transactionType,
            ),
        )
        _title.update {
            it.copy(
                text = category.title,
                selection = TextRange(category.title.length),
            )
        }
        updateEmoji(
            updatedEmoji = category.emoji,
        )
    }
}
