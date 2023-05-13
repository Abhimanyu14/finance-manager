package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface DeleteCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class DeleteCategoriesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val categoryRepository: CategoryRepository,
) : DeleteCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return categoryRepository.deleteCategories(
            categories = categories,
        )
    }
}
