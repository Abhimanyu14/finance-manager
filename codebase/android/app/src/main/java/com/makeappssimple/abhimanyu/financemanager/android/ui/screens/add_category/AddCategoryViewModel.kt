package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {
    var description by mutableStateOf(
        value = "",
    )
    var title by mutableStateOf(
        value = "",
    )

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun insertCategory() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            categoryRepository.insertCategory(
                category = Category(
                    description = description,
                    title = title,
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }
}
