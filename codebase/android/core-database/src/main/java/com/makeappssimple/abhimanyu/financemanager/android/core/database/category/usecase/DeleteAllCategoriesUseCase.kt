package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp

interface DeleteAllCategoriesUseCase {
    suspend operator fun invoke()
}

class DeleteAllCategoriesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val categoryRepository: CategoryRepository,
) : DeleteAllCategoriesUseCase {
    override suspend operator fun invoke() {
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return categoryRepository.deleteAllCategories()
    }
}
