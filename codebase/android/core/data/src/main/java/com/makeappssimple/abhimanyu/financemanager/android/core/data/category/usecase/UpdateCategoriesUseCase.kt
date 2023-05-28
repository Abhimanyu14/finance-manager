package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface UpdateCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class UpdateCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) : UpdateCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return categoryRepository.updateCategories(
            categories = categories,
        )
    }
}
