package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository

interface DeleteCategoryUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteCategoryUseCaseImpl(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) : DeleteCategoryUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return categoryRepository.deleteCategory(
            id = id,
        )
    }
}
