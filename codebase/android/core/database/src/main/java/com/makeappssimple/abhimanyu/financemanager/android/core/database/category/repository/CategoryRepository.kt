package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val categories: Flow<List<Category>>

    suspend fun getCategoriesCount(): Int

    suspend fun getCategory(
        id: Int,
    ): Category?

    suspend fun insertCategories(
        vararg categories: Category,
    )

    suspend fun updateCategories(
        vararg categories: Category,
    )

    suspend fun deleteCategory(
        id: Int,
    )

    suspend fun deleteCategories(
        vararg categories: Category,
    )
}
