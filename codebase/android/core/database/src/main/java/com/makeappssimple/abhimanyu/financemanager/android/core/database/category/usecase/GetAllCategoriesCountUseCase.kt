package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository

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
