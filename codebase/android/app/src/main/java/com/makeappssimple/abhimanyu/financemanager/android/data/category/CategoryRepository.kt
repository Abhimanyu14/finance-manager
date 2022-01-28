package com.makeappssimple.abhimanyu.financemanager.android.data.category

import com.makeappssimple.abhimanyu.financemanager.android.models.Category

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CategoryRepository(
    private val categoryDao: CategoryDao,
) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val categories = categoryDao.getCategories()

    suspend fun insertCategory(
        category: Category,
    ) {
        categoryDao.insertCategory(
            category = category,
        )
    }

    suspend fun deleteCategory(
        id: Int,
    ) {
        categoryDao.deleteCategory(
            id = id,
        )
    }

    suspend fun deleteCategories(
        vararg categories: Category,
    ) {
        categoryDao.deleteCategories(
            categories = categories,
        )
    }
}
