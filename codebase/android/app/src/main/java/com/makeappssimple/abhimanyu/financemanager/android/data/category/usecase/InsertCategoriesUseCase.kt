package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import javax.inject.Inject

class InsertCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(
        vararg categories: Category,
    ) {
        return categoryRepository.insertCategories(
            categories = categories,
        )
    }
}
