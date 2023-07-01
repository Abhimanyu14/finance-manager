package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoryRepositoryImpl : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<List<Category>> {
        return flow {
            emptyList<Category>()
        }
//        return categoryDao.getAllCategoriesFlow().map {
//            it.map { categoryEntity ->
//                categoryEntity.asExternalModel()
//            }
//        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return emptyList()
//        return categoryDao.getAllCategories().map {
//            it.asExternalModel()
//        }
    }

    override suspend fun getAllCategoriesCount(): Int {
//        return categoryDao.getAllCategoriesCount()
        return 0
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return null
//        return categoryDao.getCategory(
//            id = id,
//        )?.asExternalModel()
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ) {
//        categoryDao.insertCategories(
//            categories = categories.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ) {
//        categoryDao.updateCategories(
//            categories = categories.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun deleteCategory(
        id: Int,
    ) {
//        categoryDao.deleteCategory(
//            id = id,
//        )
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ) {
//        categoryDao.deleteCategories(
//            categories = categories.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }
}
