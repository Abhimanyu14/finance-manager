package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
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
    public val categories: StateFlow<ImmutableList<Category>> = _categories

    private val _category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    public val category: StateFlow<Category?> = _category

    public val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    public val originalTransactionType: String? = screenArgs.originalTransactionType

    public fun initViewModel() {
        fetchData()
    }

    public fun updateCategory(
        category: Category,
    ) {
        viewModelScope.launch {
            updateCategoriesUseCase(category)
            navigator.navigateUp()
        }
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    private fun fetchData() {
        getAllCategories()
        getOriginalCategory()
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
            }
        }
    }
}
