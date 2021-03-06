package com.makeappssimple.abhimanyu.financemanager.android.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val categories: Flow<List<Category>> = categoryDao.getCategories()

    override suspend fun getCategoriesCount(): Int {
        return categoryDao.getCategoriesCount()
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return categoryDao.getCategory(
            id = id,
        )
    }

    override suspend fun insertCategory(
        category: Category,
    ) {
        categoryDao.insertCategory(
            category = category,
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

    override suspend fun deleteAllCategories() {
        categoryDao.deleteAllCategories()
    }
}
