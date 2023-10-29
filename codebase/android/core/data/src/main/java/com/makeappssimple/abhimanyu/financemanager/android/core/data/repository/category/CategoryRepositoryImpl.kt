package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getAllCategoriesFlow().map {
            it.map(CategoryEntity::asExternalModel)
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories().map(CategoryEntity::asExternalModel)
    }

    override suspend fun getAllCategoriesCount(): Int {
        return categoryDao.getAllCategoriesCount()
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return categoryDao.getCategory(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ) {
        categoryDao.insertCategories(
            categories = categories.map(Category::asEntity).toTypedArray(),
        )
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ) {
        categoryDao.updateCategories(
            categories = categories.map(Category::asEntity).toTypedArray(),
        )
    }

    override suspend fun deleteCategory(
        id: Int,
    ) {
        categoryDao.deleteCategory(
            id = id,
        )
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ) {
        categoryDao.deleteCategories(
            categories = categories.map(Category::asEntity).toTypedArray(),
        )
    }
}
