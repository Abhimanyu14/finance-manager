package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override  fun getAllCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getAllCategoriesFlow()
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }

    override suspend fun getAllCategoriesCount(): Int {
        return categoryDao.getAllCategoriesCount()
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return categoryDao.getCategory(
            id = id,
        )
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ) {
        categoryDao.insertCategories(
            categories = categories,
        )
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ) {
        categoryDao.updateCategories(
            categories = categories,
        )
    }

    override suspend fun deleteCategory(
        id: Int,
    ) {
        categoryDao.deleteCategory(
            id = id,
        )
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ) {
        categoryDao.deleteCategories(
            categories = categories,
        )
    }
}
