package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
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
