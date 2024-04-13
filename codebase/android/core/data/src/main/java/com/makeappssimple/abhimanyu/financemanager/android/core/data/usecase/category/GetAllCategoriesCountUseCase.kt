package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository

public interface GetAllCategoriesCountUseCase {
    public suspend operator fun invoke(): Int
}

public class GetAllCategoriesCountUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : GetAllCategoriesCountUseCase {
    override suspend operator fun invoke(): Int {
        return categoryRepository.getAllCategoriesCount()
    }
}
