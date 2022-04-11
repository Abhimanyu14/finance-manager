package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(
        category: Category,
    ) {
        return categoryRepository.insertCategory(
            category = category,
        )
    }
}
