package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface InsertCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    ): List<Long>
}

class InsertCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) : InsertCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ): List<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return categoryRepository.insertCategories(
            categories = categories,
        )
    }
}
