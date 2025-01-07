package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<ImmutableList<Category>> {
        return categoryDao.getAllCategoriesFlow().map {
            it.map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategories(): ImmutableList<Category> {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.getAllCategories().map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategoriesCount(): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.getAllCategoriesCount()
        }
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.getCategory(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.insertCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ).toImmutableList()
        }
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
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
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.deleteCategory(
                id = id,
            ) == 1
        }
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.deleteCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ) == categories.size
        }
    }
}
