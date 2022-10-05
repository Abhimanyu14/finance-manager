package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp

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
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return categoryRepository.insertCategories(
            categories = categories,
        )
    }
}
