package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

interface InsertCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class InsertCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : InsertCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        return categoryRepository.insertCategories(
            categories = categories,
        )
    }
}
