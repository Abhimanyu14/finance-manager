package com.makeappssimple.abhimanyu.financemanager.android.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestCategories
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestCategory
import kotlinx.coroutines.runBlocking
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
    fun getCategoriesCount() {
        runBlocking {
            categoryRepository.getCategoriesCount()

            verify(
                mock = categoryDao,
            ).getCategoriesCount()
        }
    }

    @Test
    fun getCategory() {
        runBlocking {
            categoryRepository.getCategory(
                id = id,
            )

            verify(
                mock = categoryDao,
            ).getCategory(
                id = id,
            )
        }
    }

    @Test
    fun insertCategory() {
        runBlocking {
            categoryRepository.insertCategory(
                category = category,
            )

            verify(
                mock = categoryDao,
            ).insertCategory(
                category = category,
            )
        }
    }

    @Test
    fun insertCategories() {
        runBlocking {
            categoryRepository.insertCategories(
                *categories,
            )

            verify(
                mock = categoryDao,
            ).insertCategories(
                *categories,
            )
        }
    }

    @Test
    fun deleteCategory() {
        runBlocking {
            categoryRepository.deleteCategory(
                id = id,
            )

            verify(
                mock = categoryDao,
            ).deleteCategory(
                id = id,
            )
        }
    }

    @Test
    fun deleteCategories() {
        runBlocking {
            categoryRepository.deleteCategories(
                *categories,
            )

            verify(
                mock = categoryDao,
            ).deleteCategories(
                *categories,
            )
        }
    }

    @Test
    fun deleteAllCategories() {
        runBlocking {
            categoryRepository.deleteAllCategories()

            verify(
                mock = categoryDao,
            ).deleteAllCategories()
        }
    }
}
