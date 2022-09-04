package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestCategories
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestCategory
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CategoryRepositoryImplTest {
    private val categoryDao: CategoryDao = mock()
    private val id: Int = 1
    private val category: Category = getTestCategory()
    private val categories: Array<Category> = getTestCategories()
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
            id = id,
        )

        verify(
            mock = categoryDao,
        ).getCategory(
            id = id,
        )
    }

    @Test
    fun insertCategories() = runTest {
        categoryRepository.insertCategories(
            *categories,
        )

        verify(
            mock = categoryDao,
        ).insertCategories(
            *categories,
        )
    }

    @Test
    fun updateCategories() = runTest {
        categoryRepository.updateCategories(
            *categories,
        )

        verify(
            mock = categoryDao,
        ).updateCategories(
            *categories,
        )
    }

    @Test
    fun deleteCategory() = runTest {
        categoryRepository.deleteCategory(
            id = id,
        )

        verify(
            mock = categoryDao,
        ).deleteCategory(
            id = id,
        )
    }

    @Test
    fun deleteCategories() = runTest {
        categoryRepository.deleteCategories(
            *categories,
        )

        verify(
            mock = categoryDao,
        ).deleteCategories(
            *categories,
        )
    }

    @Test
    fun deleteAllCategories() = runTest {
        categoryRepository.deleteAllCategories()

        verify(
            mock = categoryDao,
        ).deleteAllCategories()
    }
}
