package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    public suspend operator fun invoke(): ImmutableList<Category> {
        return categoryRepository.getAllCategories()
    }
}
