package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface CategoryRepository {
    public fun getAllCategoriesFlow(): Flow<ImmutableList<Category>>

    public suspend fun getAllCategories(): ImmutableList<Category>

    public suspend fun getAllCategoriesCount(): Int

    public suspend fun getCategory(
        id: Int,
    ): Category?

    public suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long>

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
