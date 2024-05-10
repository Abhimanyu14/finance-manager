package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.AddOrEditCategoryScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddOrEditCategoryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val closeableCoroutineScope: CloseableCoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigator: Navigator,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
) : ScreenViewModel, ViewModel(closeableCoroutineScope) {
    private val screenArgs = AddOrEditCategoryScreenArgs(
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
    private val titleTextFieldError: MutableStateFlow<AddOrEditCategoryScreenUIError?> =
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
    }.flowOn(
        context = dispatcherProvider.io,
    )

    public val screenUIData: StateFlow<MyResult<AddOrEditCategoryScreenUIData>?> = combine(
        selectedTransactionTypeIndex,
        emoji,
        searchText,
        title,
        isValidCategoryData,
        titleTextFieldError,
    ) { flows ->
        val selectedTransactionTypeIndex = flows[0] as? Int
        val emoji = flows[1] as? String
        val searchText = flows[2] as? String
        val title = flows[3] as? TextFieldValue
        val isValidCategoryData = flows[4] as? Boolean
        val titleTextFieldErrorValue = flows[5] as? AddOrEditCategoryScreenUIError

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
                data = AddOrEditCategoryScreenUIData(
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
        scope = closeableCoroutineScope,
    )

    public fun initViewModel() {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
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
            fetchData()
        }
    }

    public fun insertCategory() {
        val emojiValue = emoji.value ?: return
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
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

    public fun updateCategory() {
        val emojiValue = emoji.value ?: return
        val categoryValue = category.value ?: return
        val updatedCategory = categoryValue.copy(
            emoji = emojiValue,
            title = title.value.text,
            transactionType = transactionTypes[selectedTransactionTypeIndex.value],
        )
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            updateCategoriesUseCase(
                updatedCategory,
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
        closeableCoroutineScope.launch(
            context = dispatcherProvider.main
        ) {
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
            closeableCoroutineScope.launch(
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
        closeableCoroutineScope.launch(
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
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            awaitAll(
                async(
                    context = dispatcherProvider.io,
                ) {
                    categories = getAllCategoriesUseCase()
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
                AddOrEditCategoryScreenUIError.CATEGORY_EXISTS
            }
            return false
        }

        if (isDefaultExpenseCategory(
                category = title.trim(),
            )
        ) {
            titleTextFieldError.update {
                AddOrEditCategoryScreenUIError.CATEGORY_EXISTS
            }
            return false
        }

        if (isDefaultInvestmentCategory(
                category = title.trim(),
            )
        ) {
            titleTextFieldError.update {
                AddOrEditCategoryScreenUIError.CATEGORY_EXISTS
            }
            return false
        }

        return if ((title.trim() != category?.title?.trim() && categories.find {
                it.title.equalsIgnoringCase(
                    other = title.trim(),
                )
            }.isNotNull())) {
            titleTextFieldError.update {
                AddOrEditCategoryScreenUIError.CATEGORY_EXISTS
            }
            false
        } else {
            true
        }
    }
}
