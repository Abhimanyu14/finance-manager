package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

interface UpdateCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class UpdateCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : UpdateCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        return categoryRepository.updateCategories(
            categories = categories,
        )
    }
}
