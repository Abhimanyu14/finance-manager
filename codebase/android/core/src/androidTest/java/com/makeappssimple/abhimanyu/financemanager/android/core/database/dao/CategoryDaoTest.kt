package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.seconds

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class CategoryDaoTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region dependencies
    private lateinit var myRoomDatabase: MyRoomDatabase
    // endregion

    // region SUT
    private lateinit var categoryDao: CategoryDao
    // endregion

    @Test
    fun getAllCategoriesFlow() = runTestWithTimeout {
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
    fun getAllCategories() = runTestWithTimeout {
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
    fun getAllCategoriesCount() = runTestWithTimeout {
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
    fun getCategory_returnsDataForValidId() = runTestWithTimeout {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getCategory(
            id = TEST_ID_2,
        )

        Assert.assertEquals(
            testCategories[1],
            result,
        )
    }

    @Test
    fun getCategory_returnsNullForInvalidId() = runTestWithTimeout {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        val result = categoryDao.getCategory(
            id = INVALID_ID,
        )

        Assert.assertNull(result)
    }

    @Test
    fun deleteCategory_deleteDataOfGivenId() = runTestWithTimeout {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        categoryDao.deleteCategory(
            id = TEST_ID_1,
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
    fun deleteCategory_noDeletionForInvalidId() = runTestWithTimeout {
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
    fun updateCategories() = runTestWithTimeout {
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
                id = INVALID_ID,
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
    fun deleteCategories() = runTestWithTimeout {
        categoryDao.insertCategories(
            categories = testCategories.toTypedArray(),
        )

        categoryDao.deleteCategories(
            testCategories[0].copy(
                emoji = "emoji 87", // Data does not match
                title = "Test",
            ),
            testCategories[1].copy(
                id = INVALID_ID,
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

    // region test setup
    private fun runTestWithTimeout(
        block: suspend TestScope.() -> Unit,
    ) = runTest(
        timeout = 3.seconds,
        testBody = {
            setUp()
            with(
                receiver = testScope,
            ) {
                block()
            }
            tearDown()
        },
    )

    private fun TestScope.setUp() {
        standardTestDispatcher = StandardTestDispatcher(
            scheduler = testScheduler,
        )
        testScope = TestScope(
            context = standardTestDispatcher,
        )
        setupSUT()
    }

    private fun setupSUT() {
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

    private fun tearDown() {
        myRoomDatabase.close()
    }
    // endregion

    companion object {
        private const val TEST_ID_1 = 123
        private const val TEST_ID_2 = 234
        private const val INVALID_ID = 987
        private val testCategories = listOf(
            CategoryEntity(
                id = TEST_ID_1,
                emoji = "emoji 1", // Not using emoji characters as test logs are not able to print it
                title = "Default",
                transactionType = TransactionType.EXPENSE,
            ),
            CategoryEntity(
                id = TEST_ID_2,
                emoji = "emoji 2",
                title = "Salary",
                transactionType = TransactionType.INCOME,
            ),
        )
    }
}
