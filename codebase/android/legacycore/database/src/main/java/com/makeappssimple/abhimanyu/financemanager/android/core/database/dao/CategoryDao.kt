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
public interface CategoryDao {
    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    public fun getAllCategoriesFlow(): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    public suspend fun getAllCategories(): List<CategoryEntity>

    @Query(value = "SELECT COUNT(*) FROM category_table")
    public suspend fun getAllCategoriesCount(): Int

    @Query(value = "SELECT * from category_table WHERE id = :id")
    public suspend fun getCategory(
        id: Int,
    ): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertCategories(
        vararg categories: CategoryEntity,
    ): List<Long>

    @Update
    public suspend fun updateCategories(
        vararg categories: CategoryEntity,
    ): Int

    @Query(value = "DELETE FROM category_table WHERE id = :id")
    public suspend fun deleteCategory(
        id: Int,
    ): Int

    @Delete
    public suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    ): Int

    @Query(value = "DELETE FROM category_table")
    public suspend fun deleteAllCategories(): Int
}
