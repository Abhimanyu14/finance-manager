package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

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
