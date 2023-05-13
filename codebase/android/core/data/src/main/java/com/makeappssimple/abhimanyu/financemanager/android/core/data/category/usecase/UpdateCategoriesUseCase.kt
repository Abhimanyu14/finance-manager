package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface UpdateCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class UpdateCategoriesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val categoryRepository: CategoryRepository,
) : UpdateCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return categoryRepository.updateCategories(
            categories = categories,
        )
    }
}
