package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository

interface GetAllCategoriesCountUseCase {
    suspend operator fun invoke(): Int
}

class GetAllCategoriesCountUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetAllCategoriesCountUseCase {
    override suspend operator fun invoke(): Int {
        return categoryRepository.getAllCategoriesCount()
    }
}
