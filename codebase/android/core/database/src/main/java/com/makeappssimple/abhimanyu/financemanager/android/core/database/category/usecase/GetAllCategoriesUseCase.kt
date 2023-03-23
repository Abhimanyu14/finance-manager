package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

interface GetAllCategoriesUseCase {
    suspend operator fun invoke(): List<Category>
}

class GetAllCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetAllCategoriesUseCase {
    override suspend operator fun invoke(): List<Category> {
        return categoryRepository.getAllCategories()
    }
}
