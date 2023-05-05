package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
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
