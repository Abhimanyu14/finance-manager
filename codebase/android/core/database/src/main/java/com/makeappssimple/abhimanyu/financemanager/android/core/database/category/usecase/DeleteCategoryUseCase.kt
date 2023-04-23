package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteCategoryUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteCategoryUseCaseImpl(
    private val dataStore: MyDataStore,
    private val categoryRepository: CategoryRepository,
) : DeleteCategoryUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return categoryRepository.deleteCategory(
            id = id,
        )
    }
}
