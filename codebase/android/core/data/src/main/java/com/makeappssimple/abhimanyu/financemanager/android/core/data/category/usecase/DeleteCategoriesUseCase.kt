package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface DeleteCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class DeleteCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) : DeleteCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return categoryRepository.deleteCategories(
            categories = categories,
        )
    }
}
