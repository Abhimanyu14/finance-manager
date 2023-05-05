package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category

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
