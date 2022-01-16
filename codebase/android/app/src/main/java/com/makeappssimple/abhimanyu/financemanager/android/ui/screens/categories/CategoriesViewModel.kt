package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import com.makeappssimple.abhimanyu.financemanager.android.data.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {
    var categories: Flow<List<Category>> = categoryRepository.categories

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
