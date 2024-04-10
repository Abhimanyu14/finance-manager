package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getAllCategoriesFlow().map {
            it.map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.getAllCategories().map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategoriesCount(): Int {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.getAllCategoriesCount()
        }
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.getCategory(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.insertCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.updateCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun deleteCategory(
        id: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.deleteCategory(
                id = id,
            )
        }
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            categoryDao.deleteCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            )
        }
    }
}
