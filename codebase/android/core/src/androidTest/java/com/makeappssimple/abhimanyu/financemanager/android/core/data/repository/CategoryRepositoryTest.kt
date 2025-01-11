package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.getTestCategories
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import javax.inject.Inject

@Ignore("Fix Hilt")
@HiltAndroidTest
internal class CategoryRepositoryTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val categoryDao: CategoryDao = mock()
    private val testId: Int = 1
    private val testCategories: Array<Category> = getTestCategories()
    private lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        categoryRepository = CategoryRepositoryImpl(
            categoryDao = categoryDao,
            dispatcherProvider = dispatcherProvider,
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
            categories = testCategories,
        )

        verify(
            mock = categoryDao,
        ).insertCategories(
            categories = testCategories.map(
                transform = Category::asEntity,
            ).toTypedArray(),
        )
    }

    @Test
    fun updateCategories() = runTest {
        categoryRepository.updateCategories(
            categories = testCategories,
        )

        verify(
            mock = categoryDao,
        ).updateCategories(
            categories = testCategories.map(
                transform = Category::asEntity,
            ).toTypedArray(),
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
            categories = testCategories,
        )

        verify(
            mock = categoryDao,
        ).deleteCategories(
            categories = testCategories.map(
                transform = Category::asEntity,
            ).toTypedArray(),
        )
    }
}
