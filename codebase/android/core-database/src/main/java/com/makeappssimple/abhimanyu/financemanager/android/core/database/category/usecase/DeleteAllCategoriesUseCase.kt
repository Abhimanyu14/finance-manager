package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteAllCategoriesUseCase {
    suspend operator fun invoke()
}

class DeleteAllCategoriesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val categoryRepository: CategoryRepository,
) : DeleteAllCategoriesUseCase {
    override suspend operator fun invoke() {
        dataStore.updateLastDataChangeTimestamp()
        return categoryRepository.deleteAllCategories()
    }
}
