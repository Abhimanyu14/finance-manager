package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestCategories
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import javax.inject.Inject

@HiltAndroidTest
public class CategoryRepositoryTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val categoryDao: CategoryDao = mock()
    private val testId: Int = 1
    private val testCategories: Array<Category> = getTestCategories()
    private lateinit var categoryRepository: CategoryRepository

    @Inject
    public lateinit var dispatcherProvider: DispatcherProvider

    @Before
    public fun setUp() {
        categoryRepository = CategoryRepositoryImpl(
            categoryDao = categoryDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Test
    public fun getAllCategoriesFlow() {
        categoryRepository.getAllCategoriesFlow()

        verify(
            mock = categoryDao,
        ).getAllCategoriesFlow()
    }

    @Ignore("Fix this test")
    @Test
    public fun getAllCategories(): TestResult = runTest {
        categoryRepository.getAllCategories()

        verify(
            mock = categoryDao,
        ).getAllCategories()
    }

    @Test
    public fun getAllCategoriesCount(): TestResult = runTest {
        categoryRepository.getAllCategoriesCount()

        verify(
            mock = categoryDao,
        ).getAllCategoriesCount()
    }

    @Test
    public fun getCategory(): TestResult = runTest {
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
    public fun insertCategories(): TestResult = runTest {
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
    public fun updateCategories(): TestResult = runTest {
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
    public fun deleteCategory(): TestResult = runTest {
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
    public fun deleteCategories(): TestResult = runTest {
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
