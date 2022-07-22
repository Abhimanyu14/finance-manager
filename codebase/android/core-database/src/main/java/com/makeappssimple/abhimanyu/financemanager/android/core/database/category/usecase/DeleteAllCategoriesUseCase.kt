package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

interface DeleteAllCategoriesUseCase {
    suspend operator fun invoke()
}

class DeleteAllCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : DeleteAllCategoriesUseCase {
    override suspend operator fun invoke() {
        return categoryRepository.deleteAllCategories()
    }
}
