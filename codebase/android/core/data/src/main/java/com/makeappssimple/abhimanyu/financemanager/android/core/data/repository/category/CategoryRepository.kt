package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category

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
    ): List<Long>

    suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean

    suspend fun deleteCategory(
        id: Int,
    ): Boolean

    suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean
}
