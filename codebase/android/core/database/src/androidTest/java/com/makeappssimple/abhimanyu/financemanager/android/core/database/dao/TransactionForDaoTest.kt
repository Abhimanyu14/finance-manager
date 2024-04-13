package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
public class TransactionForDaoTest {
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var transactionForDao: TransactionForDao

    @get:Rule
    public val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    @Before
    public fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        transactionForDao = myRoomDatabase.transactionForDao()
    }

    @After
    public fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    public fun getAllTransactionForValues(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getAllTransactionForValues()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testTransactionForValues.toSet(),
            result.toSet(),
        )
    }

    @Test
    public fun getAllTransactionForValuesFlow(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getAllTransactionForValuesFlow().first()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testTransactionForValues[0],
            result[0],
        )
        Assert.assertEquals(
            testTransactionForValues[1],
            result[1],
        )
    }

    @Test
    public fun getTransactionForValuesCount(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getTransactionForValuesCount()

        Assert.assertEquals(
            testTransactionForValues.size,
            result,
        )
    }

    @Test
    public fun getTransactionFor_returnsDataForValidId(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getTransactionFor(
            id = testId1,
        )

        Assert.assertEquals(
            testTransactionForValues[0],
            result,
        )
    }

    @Test
    public fun getTransactionFor_returnsNullForInvalidId(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getTransactionFor(
            id = invalidId,
        )

        Assert.assertNull(result)
    }

    @Test
    public fun updateTransactionForValues(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val testTitle1 = "Tim"
        val testTitle2 = "Rock"
        transactionForDao.updateTransactionForValues(
            testTransactionForValues[0].copy(
                id = testId1,
                title = testTitle1,
            ),
            testTransactionForValues[1].copy(
                id = testId2,
                title = testTitle2,
            ),
        )
        val result = transactionForDao.getAllTransactionForValues()

        Assert.assertEquals(
            testTitle1,
            result[0].title,
        )
        Assert.assertEquals(
            testTitle2,
            result[1].title,
        )
    }

    @Test
    public fun deleteTransactionFor_deleteDataOfGivenId(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        transactionForDao.deleteTransactionFor(
            id = testId1,
        )
        val result = transactionForDao.getAllTransactionForValues()

        Assert.assertEquals(
            1,
            result.size,
        )
        Assert.assertEquals(
            testTransactionForValues[1],
            result[0],
        )
    }

    @Test
    public fun deleteTransactionFor_noDeletionForInvalidId(): TestResult = runTest {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        transactionForDao.deleteTransactionFor(
            id = invalidId,
        )
        val result = transactionForDao.getAllTransactionForValues()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testTransactionForValues,
            result,
        )
    }

    public companion object {
        private const val testId1 = 123
        private const val testId2 = 234
        private const val invalidId = 987
        private val testTransactionForValues = listOf(
            TransactionForEntity(
                id = testId1,
                title = "Sam",
            ),
            TransactionForEntity(
                id = testId2,
                title = "Jim",
            ),
        )
    }
}
