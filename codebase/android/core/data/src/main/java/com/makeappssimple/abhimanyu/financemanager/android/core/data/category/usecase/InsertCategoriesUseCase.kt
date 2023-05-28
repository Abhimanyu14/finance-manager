package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface InsertCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class InsertCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) : InsertCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return categoryRepository.insertCategories(
            categories = categories,
        )
    }
}
