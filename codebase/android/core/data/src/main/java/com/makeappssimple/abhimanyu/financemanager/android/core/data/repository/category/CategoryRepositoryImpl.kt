package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.CoroutineScope
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
        return executeOnIoDispatcher {
            categoryDao.getAllCategories().map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategoriesCount(): Int {
        return executeOnIoDispatcher {
            categoryDao.getAllCategoriesCount()
        }
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return executeOnIoDispatcher {
            categoryDao.getCategory(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ): List<Long> {
        return executeOnIoDispatcher {
            categoryDao.insertCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean {
        return executeOnIoDispatcher {
            categoryDao.updateCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ) == categories.size
        }
    }

    override suspend fun deleteCategory(
        id: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            categoryDao.deleteCategory(
                id = id,
            ) == 1
        }
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean {
        return executeOnIoDispatcher {
            categoryDao.deleteCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ) == categories.size
        }
    }

    private suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        return withContext(
            context = dispatcherProvider.io,
            block = block,
        )
    }
}
