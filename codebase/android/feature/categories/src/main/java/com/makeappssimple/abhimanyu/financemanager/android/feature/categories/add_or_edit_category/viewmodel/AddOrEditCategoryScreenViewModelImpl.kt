package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.AddOrEditCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddOrEditCategoryScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllEmojisUseCase: GetAllEmojisUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
) : AddOrEditCategoryScreenViewModel, ViewModel() {
    private val addOrEditCategoryScreenArgs: AddOrEditCategoryScreenArgs =
        AddOrEditCategoryScreenArgs(
            savedStateHandle = savedStateHandle,
            stringDecoder = stringDecoder,
        )
    private lateinit var categories: List<Category>
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    private val transactionTypes: List<TransactionType> = listOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    private val selectedTransactionTypeIndex = MutableStateFlow(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    private val emoji: MutableStateFlow<String?> = MutableStateFlow(
        value = if (addOrEditCategoryScreenArgs.originalCategoryId.isNull()) {
            EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
        } else {
            null
        },
    )
    private val searchText = MutableStateFlow(
        value = "",
    )
    private var emojis: List<Emoji> = emptyList()
    private val emojiGroups: Flow<Map<String, List<Emoji>>> = searchText.map { searchTextValue ->
        emojis.filter { emoji ->
            if (searchTextValue.isBlank()) {
                true
            } else {
                emoji.unicodeName.contains(searchTextValue)
            }
        }.groupBy { emoji ->
            emoji.group
        }.filter { (_, emojis) ->
            emojis.isNotEmpty()
        }
    }.flowOn(
        context = dispatcherProvider.io,
    )
    private val isValidCategoryData = combine(
        title,
        category,
    ) {
            title,
            category,
        ->
        checkIfCategoryDataIsValid(
            title = title.text,
            category = category,
        )
    }.flowOn(
        context = dispatcherProvider.io,
    )

    override val screenUIData: StateFlow<MyResult<AddOrEditCategoryScreenUIData>?> = combine(
        selectedTransactionTypeIndex,
        emojiGroups,
        emoji,
        searchText,
        title,
        isValidCategoryData,
    ) { flows ->
        val selectedTransactionTypeIndex = flows[0] as? Int
        val emojiGroups = flows[1] as? Map<String, List<Emoji>>
        val emoji = flows[2] as? String
        val searchText = flows[3] as? String
        val title = flows[4] as? TextFieldValue
        val isValidCategoryData = flows[5] as? Boolean

        if (
            selectedTransactionTypeIndex.isNull() ||
            emojiGroups.isNull() ||
            emoji.isNull() ||
            searchText.isNull() ||
            title.isNull() ||
            isValidCategoryData.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddOrEditCategoryScreenUIData(
                    isValidCategoryData = isValidCategoryData,
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    emojiGroups = emojiGroups,
                    transactionTypes = transactionTypes,
                    emoji = emoji,
                    searchText = searchText,
                    title = title,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            getOriginalCategory()
            addOrEditCategoryScreenArgs.originalTransactionType?.let { originalTransactionType ->
                updateSelectedTransactionTypeIndex(
                    updatedIndex = transactionTypes.indexOf(
                        element = TransactionType.values().find { transactionType ->
                            transactionType.title == originalTransactionType
                        },
                    )
                )
            }
            fetchData()
        }
    }

    override fun insertCategory() {
        val emojiValue = emoji.value ?: return
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertCategoriesUseCase(
                Category(
                    emoji = emojiValue,
                    title = title.value.text,
                    transactionType = transactionTypes[selectedTransactionTypeIndex.value],
                ),
            )

            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun updateCategory() {
        val emojiValue = emoji.value ?: return
        val categoryValue = category.value ?: return
        val updatedCategory = categoryValue.copy(
            emoji = emojiValue,
            title = title.value.text,
            transactionType = transactionTypes[selectedTransactionTypeIndex.value],
        )
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            updateCategoriesUseCase(
                updatedCategory,
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
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
        viewModelScope.launch(
            context = dispatcherProvider.main
        ) {
            title.update {
                updatedTitle
            }
        }
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    ) {
        selectedTransactionTypeIndex.value = updatedIndex
    }

    override fun updateEmoji(
        updatedEmoji: String,
    ) {
        emoji.value = updatedEmoji
    }

    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        searchText.value = updatedSearchText
    }

    private fun getOriginalCategory() {
        addOrEditCategoryScreenArgs.originalCategoryId?.let { id ->
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
    }

    private fun updateInitialCategoryValue() {
        val category = category.value ?: return
        updateSelectedTransactionTypeIndex(
            updatedIndex = transactionTypes.indexOf(
                element = category.transactionType,
            ),
        )
        viewModelScope.launch(
            context = dispatcherProvider.main
        ) {
            title.update {
                it.copy(
                    text = category.title,
                    selection = TextRange(category.title.length),
                )
            }
        }
        updateEmoji(
            updatedEmoji = category.emoji,
        )
    }

    private fun fetchData() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            awaitAll(
                async(
                    context = dispatcherProvider.io,
                ) {
                    categories = getAllCategoriesUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    emojis = getAllEmojisUseCase()
                },
            )
        }
    }

    private fun checkIfCategoryDataIsValid(
        title: String,
        category: Category?,
    ): Boolean {
        // TODO-Abhi: Error message - "Title can not be empty"
        if (title.isBlank()) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (isDefaultIncomeCategory(
                category = title.trim(),
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (isDefaultExpenseCategory(
                category = title.trim(),
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (isDefaultInvestmentCategory(
                category = title.trim(),
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        return !(title.trim() != category?.title?.trim() && categories.find {
            it.title.equalsIgnoringCase(
                other = title.trim(),
            )
        }.isNotNull())
    }
}
