package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class CategoryDaoTest {
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var categoryDao: CategoryDao

    @get:Rule
    val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        categoryDao = myRoomDatabase.categoryDao()
    }

    @After
    fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    fun getAllCategories() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getAllCategories()

        Assert.assertEquals(
            testCategories.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllCategoriesFlow() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getAllCategoriesFlow().first()

        Assert.assertEquals(
            testCategories.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllCategoriesCount() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getAllCategoriesCount()

        Assert.assertEquals(
            testCategories.size,
            result,
        )
    }

    @Test
    fun getCategory_returnsDataForValidId() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getCategory(
            id = testId2,
        )

        Assert.assertEquals(
            testCategories[1],
            result,
        )
    }

    @Test
    fun getCategory_returnsNullForInvalidId() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getCategory(
            id = invalidId,
        )

        Assert.assertNull(result)
    }

    @Test
    fun deleteCategory_deleteDataOfGivenId() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        categoryDao.deleteCategory(
            id = testId1,
        )
        val result = categoryDao.getAllCategories()

        Assert.assertEquals(
            1,
            result.size,
        )
        Assert.assertEquals(
            testCategories[1],
            result[0],
        )
    }

    @Test
    fun deleteCategory_noDeletionForInvalidId() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        categoryDao.deleteCategory(
            id = 9348,
        )
        val result = categoryDao.getAllCategories()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testCategories,
            result,
        )
    }

    @Test
    fun updateCategories() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val testCategoryEmoji1 = "emoji 4"
        val testCategoryTitle1 = "Test 1"
        categoryDao.updateCategories(
            testCategories[0].copy(
                emoji = testCategoryEmoji1,
                title = testCategoryTitle1,
            ),
            testCategories[1].copy(
                id = invalidId,
            ),
        )
        val result = categoryDao.getAllCategories()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testCategories[1],
            result[1],
        )
        Assert.assertEquals(
            testCategoryEmoji1,
            result[0].emoji,
        )
        Assert.assertEquals(
            testCategoryTitle1,
            result[0].title,
        )
    }

    @Test
    fun deleteCategories() = runTest {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        categoryDao.deleteCategories(
            testCategories[0].copy(
                emoji = "emoji 87", // Data does not match
                title = "Test",
            ),
            testCategories[1].copy(
                id = invalidId,
            ),
        )
        val result = categoryDao.getAllCategories()

        Assert.assertEquals(
            1,
            result.size,
        )
        Assert.assertEquals(
            testCategories[1],
            result[0],
        )
    }

    companion object {
        private const val testId1 = 123
        private const val testId2 = 234
        private const val invalidId = 987
        private val testCategories = listOf(
            CategoryEntity(
                id = testId1,
                emoji = "emoji 1", // Not using emoji characters as test logs are not able to print it
                title = "Default",
                transactionType = TransactionType.EXPENSE,
            ),
            CategoryEntity(
                id = testId2,
                emoji = "emoji 2",
                title = "Salary",
                transactionType = TransactionType.INCOME,
            ),
        )
    }
}
