package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesFlowUseCase {
    operator fun invoke(): Flow<List<Category>>
}

class GetAllCategoriesFlowUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetAllCategoriesFlowUseCase {
    override operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.getAllCategoriesFlow()
    }
}
