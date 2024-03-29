package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    fun getAllCategoriesFlow(): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query(value = "SELECT COUNT(*) FROM category_table")
    suspend fun getAllCategoriesCount(): Int

    @Query(value = "SELECT * from category_table WHERE id = :id")
    suspend fun getCategory(
        id: Int,
    ): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(
        vararg categories: CategoryEntity,
    )

    @Update
    suspend fun updateCategories(
        vararg categories: CategoryEntity,
    )

    @Query(value = "DELETE FROM category_table WHERE id = :id")
    suspend fun deleteCategory(
        id: Int,
    )

    @Delete
    suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    )

    @Query(value = "DELETE FROM category_table")
    suspend fun deleteAllCategories()
}
