package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestCategories
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CategoryRepositoryTest {
    private val categoryDao: CategoryDao = mock()
    private val testId: Int = 1
    private val testCategories: Array<Category> = getTestCategories()
    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun setUp() {
        categoryRepository =
            CategoryRepositoryImpl(
                categoryDao = categoryDao,
            )
    }

    @Test
    fun getAllCategoriesFlow() {
        categoryRepository.getAllCategoriesFlow()

        verify(
            mock = categoryDao,
        ).getAllCategoriesFlow()
    }

    @Ignore("Fix this test")
    @Test
    fun getAllCategories() = runTest {
        categoryRepository.getAllCategories()

        verify(
            mock = categoryDao,
        ).getAllCategories()
    }

    @Test
    fun getAllCategoriesCount() = runTest {
        categoryRepository.getAllCategoriesCount()

        verify(
            mock = categoryDao,
        ).getAllCategoriesCount()
    }

    @Test
    fun getCategory() = runTest {
        categoryRepository.getCategory(
            id = testId,
        )

        verify(
            mock = categoryDao,
        ).getCategory(
            id = testId,
        )
    }

    @Test
    fun insertCategories() = runTest {
        categoryRepository.insertCategories(
            *testCategories,
        )

        verify(
            mock = categoryDao,
        ).insertCategories(
            *testCategories.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @Test
    fun updateCategories() = runTest {
        categoryRepository.updateCategories(
            *testCategories,
        )

        verify(
            mock = categoryDao,
        ).updateCategories(
            *testCategories.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @Test
    fun deleteCategory() = runTest {
        categoryRepository.deleteCategory(
            id = testId,
        )

        verify(
            mock = categoryDao,
        ).deleteCategory(
            id = testId,
        )
    }

    @Test
    fun deleteCategories() = runTest {
        categoryRepository.deleteCategories(
            *testCategories,
        )

        verify(
            mock = categoryDao,
        ).deleteCategories(
            *testCategories.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }
}
