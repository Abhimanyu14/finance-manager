package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow

public interface CategoryRepository {
    public fun getAllCategoriesFlow(): Flow<List<Category>>

    public suspend fun getAllCategories(): List<Category>

    public suspend fun getAllCategoriesCount(): Int

    public suspend fun getCategory(
        id: Int,
    ): Category?

    public suspend fun insertCategories(
        vararg categories: Category,
    ): List<Long>

    public suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean

    public suspend fun deleteCategory(
        id: Int,
    ): Boolean

    public suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean
}
