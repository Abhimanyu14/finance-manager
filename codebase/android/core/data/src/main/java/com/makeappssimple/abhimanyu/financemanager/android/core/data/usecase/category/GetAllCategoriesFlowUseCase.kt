package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow

public interface GetAllCategoriesFlowUseCase {
    public operator fun invoke(): Flow<List<Category>>
}

public class GetAllCategoriesFlowUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetAllCategoriesFlowUseCase {
    override operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.getAllCategoriesFlow()
    }
}
