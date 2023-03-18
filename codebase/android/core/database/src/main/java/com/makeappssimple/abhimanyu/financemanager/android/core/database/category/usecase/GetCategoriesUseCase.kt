package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
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
