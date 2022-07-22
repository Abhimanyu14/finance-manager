package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

interface InsertCategoryUseCase {
    suspend operator fun invoke(
        category: Category,
    )
}

class InsertCategoryUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : InsertCategoryUseCase {
    override suspend operator fun invoke(
        category: Category,
    ) {
        return categoryRepository.insertCategory(
            category = category,
        )
    }
}
