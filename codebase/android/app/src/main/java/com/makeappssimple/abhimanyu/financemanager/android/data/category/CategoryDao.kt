package com.makeappssimple.abhimanyu.financemanager.android.data.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    fun getCategories(): Flow<List<Category>>

    @Query(value = "SELECT * from category_table WHERE id = :id")
    suspend fun getCategory(
        id: Int,
    ): Category?

    @Query(value = "SELECT COUNT(*) FROM category_table")
    suspend fun getCategoriesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(
        category: Category,
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(
        vararg categories: Category,
    )

    @Query(value = "DELETE FROM category_table WHERE id = :id")
    suspend fun deleteCategory(
        id: Int,
    )

    @Delete
    suspend fun deleteCategories(
        vararg categories: Category,
    )

    @Query(value = "DELETE FROM category_table")
    suspend fun deleteAllCategories()
}
