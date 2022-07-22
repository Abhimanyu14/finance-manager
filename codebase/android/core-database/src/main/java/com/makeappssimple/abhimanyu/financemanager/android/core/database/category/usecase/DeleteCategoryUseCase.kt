package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

interface DeleteCategoryUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteCategoryUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : DeleteCategoryUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        return categoryRepository.deleteCategory(
            id = id,
        )
    }
}
