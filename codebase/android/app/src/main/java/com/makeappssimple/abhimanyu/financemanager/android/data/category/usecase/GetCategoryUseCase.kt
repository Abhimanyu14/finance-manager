package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category

interface GetCategoryUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Category?
}

class GetCategoryUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetCategoryUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Category? {
        return categoryRepository.getCategory(
            id = id,
        )
    }
}
