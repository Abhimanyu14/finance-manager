package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

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
