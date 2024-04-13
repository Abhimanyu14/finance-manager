package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

public interface UpdateCategoriesUseCase {
    public suspend operator fun invoke(
        vararg categories: Category,
    ): Boolean
}

public class UpdateCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) : UpdateCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return categoryRepository.updateCategories(
            categories = categories,
        )
    }
}
