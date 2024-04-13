package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FakeCategoryDaoImpl : CategoryDao {
    override fun getAllCategoriesFlow(): Flow<List<CategoryEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return emptyList()
    }

    override suspend fun getAllCategoriesCount(): Int {
        return 0
    }

    override suspend fun getCategory(
        id: Int,
    ): CategoryEntity? {
        return null
    }

    override suspend fun insertCategories(
        vararg categories: CategoryEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateCategories(
        vararg categories: CategoryEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteCategory(
        id: Int,
    ): Int {
        return 0
    }

    override suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteAllCategories(): Int {
        return 0
    }
}
