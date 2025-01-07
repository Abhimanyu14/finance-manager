package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category.fake

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeCategoryRepositoryImpl : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<ImmutableList<Category>> {
        return flow {
            persistentListOf<Category>()
        }
    }

    override suspend fun getAllCategories(): ImmutableList<Category> {
        return persistentListOf()
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
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean {
        return false
    }

    override suspend fun deleteCategory(
        id: Int,
    ): Boolean {
        return false
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean {
        return false
    }
}
