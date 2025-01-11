package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllCategoriesFlowUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    public operator fun invoke(): Flow<ImmutableList<Category>> {
        return categoryRepository.getAllCategoriesFlow()
    }
}
