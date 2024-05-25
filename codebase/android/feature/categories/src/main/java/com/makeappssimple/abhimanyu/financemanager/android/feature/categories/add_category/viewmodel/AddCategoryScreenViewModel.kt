package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen.AddCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen.AddCategoryScreenUIError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.AddCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddCategoryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    private val categories: MutableList<Category> = mutableListOf()
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    private val transactionTypes: List<TransactionType> = listOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val titleTextFieldError: MutableStateFlow<AddCategoryScreenUIError?> =
        MutableStateFlow(
            value = null,
        )
    private val selectedTransactionTypeIndex = MutableStateFlow(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    private val emoji: MutableStateFlow<String?> = MutableStateFlow(
        value = if (screenArgs.originalCategoryId.isNull()) {
            EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
        } else {
            null
        },
    )
    private val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
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
    }

    public val screenUIData: StateFlow<MyResult<AddCategoryScreenUIData>?> = combine(
        selectedTransactionTypeIndex,
        emoji,
        searchText,
        title,
        isValidCategoryData,
        titleTextFieldError,
    ) {
            selectedTransactionTypeIndex,
            emoji,
            searchText,
            title,
            isValidCategoryData,
            titleTextFieldErrorValue,
        ->

        if (
            selectedTransactionTypeIndex.isNull() ||
            emoji.isNull() ||
            searchText.isNull() ||
            title.isNull() ||
            isValidCategoryData.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddCategoryScreenUIData(
                    isCtaButtonEnabled = isValidCategoryData,
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    validTransactionTypes = transactionTypes,
                    emoji = emoji,
                    emojiSearchText = searchText,
                    title = title,
                    titleTextFieldError = titleTextFieldErrorValue,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    public fun initViewModel() {
        fetchData()
    }

    public fun insertCategory() {
        val emojiValue = emoji.value ?: return
        viewModelScope.launch {
            insertCategoriesUseCase(
                Category(
                    emoji = emojiValue,
                    title = title.value.text,
                    transactionType = transactionTypes[selectedTransactionTypeIndex.value],
                ),
            )
            navigator.navigateUp()
        }
    }

    public fun clearTitle() {
        updateTitle(
            updatedTitle = title.value.copy(
                text = "",
            ),
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        viewModelScope.launch {
            title.update {
                updatedTitle
            }
        }
    }

    public fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    ) {
        selectedTransactionTypeIndex.value = updatedIndex
    }

    public fun updateEmoji(
        updatedEmoji: String,
    ) {
        emoji.value = updatedEmoji
    }

    public fun updateSearchText(
        updatedSearchText: String,
    ) {
        searchText.value = updatedSearchText
    }

    private fun getOriginalCategory() {
        screenArgs.originalCategoryId?.let { id ->
            viewModelScope.launch {
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
        viewModelScope.launch {
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
        getOriginalCategory()
        screenArgs.originalTransactionType?.let { originalTransactionType ->
            updateSelectedTransactionTypeIndex(
                updatedIndex = transactionTypes.indexOf(
                    element = TransactionType.entries.find { transactionType ->
                        transactionType.title == originalTransactionType
                    },
                )
            )
        }
        viewModelScope.launch {
            awaitAll(
                async {
                    categories.clear()
                    categories.addAll(getAllCategoriesUseCase())
                },
            )
        }
    }

    private fun checkIfCategoryDataIsValid(
        title: String,
        category: Category?,
    ): Boolean {
        titleTextFieldError.update {
            null
        }

        if (title.isBlank()) {
            return false
        }

        if (isDefaultIncomeCategory(
                category = title.trim(),
            )
        ) {
            titleTextFieldError.update {
                AddCategoryScreenUIError.CATEGORY_EXISTS
            }
            return false
        }

        if (isDefaultExpenseCategory(
                category = title.trim(),
            )
        ) {
            titleTextFieldError.update {
                AddCategoryScreenUIError.CATEGORY_EXISTS
            }
            return false
        }

        if (isDefaultInvestmentCategory(
                category = title.trim(),
            )
        ) {
            titleTextFieldError.update {
                AddCategoryScreenUIError.CATEGORY_EXISTS
            }
            return false
        }

        return if ((title.trim() != category?.title?.trim() && categories.find {
                it.title.equalsIgnoringCase(
                    other = title.trim(),
                )
            }.isNotNull())) {
            titleTextFieldError.update {
                AddCategoryScreenUIError.CATEGORY_EXISTS
            }
            false
        } else {
            true
        }
    }
}
