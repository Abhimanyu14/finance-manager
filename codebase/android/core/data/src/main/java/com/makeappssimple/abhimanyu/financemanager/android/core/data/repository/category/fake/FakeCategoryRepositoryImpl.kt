package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoryRepositoryImpl : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<List<Category>> {
        return flow {
            emptyList<Category>()
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return emptyList()
    }

    override suspend fun getAllCategoriesCount(): Int {
        return 0
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return null
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ) {
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ) {
    }

    override suspend fun deleteCategory(
        id: Int,
    ) {
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ) {
    }
}
