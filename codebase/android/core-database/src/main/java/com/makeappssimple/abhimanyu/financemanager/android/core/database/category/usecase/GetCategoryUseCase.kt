package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

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
