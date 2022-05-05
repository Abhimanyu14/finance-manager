package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import kotlinx.coroutines.flow.Flow

interface GetCategoriesUseCase {
    operator fun invoke(): Flow<List<Category>>
}

class GetCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetCategoriesUseCase {
    override operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.categories
    }
}
