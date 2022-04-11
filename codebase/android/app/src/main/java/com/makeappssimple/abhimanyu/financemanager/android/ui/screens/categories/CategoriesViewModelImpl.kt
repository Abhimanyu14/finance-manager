package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    override val navigationManager: NavigationManager,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
) : CategoriesViewModel, ViewModel() {
    override val categories: Flow<List<Category>> = getCategoriesUseCase()

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun deleteCategory(
        id: Int,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            deleteCategoryUseCase(
                id = id,
            )
        }
    }
}
