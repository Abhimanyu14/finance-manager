package com.makeappssimple.abhimanyu.financemanager.android.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.category.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.models.Category

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
): CategoryRepository {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val categories = categoryDao.getCategories()

    override suspend fun insertCategory(
        category: Category,
    ) {
        categoryDao.insertCategory(
            category = category,
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
