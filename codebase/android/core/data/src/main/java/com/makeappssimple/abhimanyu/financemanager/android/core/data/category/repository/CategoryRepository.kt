package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategoriesFlow(): Flow<List<Category>>

    suspend fun getAllCategories(): List<Category>

    suspend fun getAllCategoriesCount(): Int

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
