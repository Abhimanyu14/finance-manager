package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

interface InsertCategoriesUseCase {
    suspend operator fun invoke(
        vararg categories: Category,
    )
}

class InsertCategoriesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val categoryRepository: CategoryRepository,
) : InsertCategoriesUseCase {
    override suspend operator fun invoke(
        vararg categories: Category,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return categoryRepository.insertCategories(
            categories = categories,
        )
    }
}
