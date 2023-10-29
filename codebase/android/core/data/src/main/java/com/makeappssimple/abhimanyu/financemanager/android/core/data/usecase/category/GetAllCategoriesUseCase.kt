package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
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
