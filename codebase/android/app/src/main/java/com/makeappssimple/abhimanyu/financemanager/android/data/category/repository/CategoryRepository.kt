package com.makeappssimple.abhimanyu.financemanager.android.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val categories: Flow<List<Category>>

    suspend fun insertCategory(
        category: Category,
    )

    suspend fun deleteCategory(
        id: Int,
    )

    suspend fun deleteCategories(
        vararg categories: Category,
    )
}
