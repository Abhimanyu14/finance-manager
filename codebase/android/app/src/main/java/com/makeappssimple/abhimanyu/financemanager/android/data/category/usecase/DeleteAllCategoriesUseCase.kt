package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository

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
