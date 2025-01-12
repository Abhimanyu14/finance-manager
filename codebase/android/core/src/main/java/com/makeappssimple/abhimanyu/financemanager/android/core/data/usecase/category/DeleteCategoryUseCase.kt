package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import javax.inject.Inject

public class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val myPreferencesRepository: MyPreferencesRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Boolean {
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return categoryRepository.deleteCategory(
            id = id,
        )
    }
}
