package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

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
