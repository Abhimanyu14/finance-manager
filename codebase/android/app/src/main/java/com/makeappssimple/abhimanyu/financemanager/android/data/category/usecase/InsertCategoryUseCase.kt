package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category

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
