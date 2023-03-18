package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestCategories
import kotlinx.coroutines.test.runTest
import org.junit.Before
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
        categoryRepository = CategoryRepositoryImpl(
            categoryDao = categoryDao,
        )
    }

    @Test
    fun getCategories() {
        categoryRepository.categories

        verify(
            mock = categoryDao,
        ).getCategories()
    }

    @Test
    fun getCategoriesCount() = runTest {
        categoryRepository.getCategoriesCount()

        verify(
            mock = categoryDao,
        ).getCategoriesCount()
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
            *testCategories,
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
            *testCategories,
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
            *testCategories,
        )
    }
}
