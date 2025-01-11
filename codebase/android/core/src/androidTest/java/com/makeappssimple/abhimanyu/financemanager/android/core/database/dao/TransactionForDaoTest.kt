package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
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
internal class TransactionForDaoTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region dependencies
    private lateinit var myRoomDatabase: MyRoomDatabase
    // endregion

    // region SUT
    private lateinit var transactionForDao: TransactionForDao
    // endregion

    @Test
    fun getAllTransactionForValues() = runTestWithTimeout {
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
    fun getAllTransactionForValuesFlow() = runTestWithTimeout {
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
    fun getTransactionForValuesCount() = runTestWithTimeout {
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
    fun getTransactionFor_returnsDataForValidId() = runTestWithTimeout {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getTransactionFor(
            id = TEST_ID_1,
        )

        Assert.assertEquals(
            testTransactionForValues[0],
            result,
        )
    }

    @Test
    fun getTransactionFor_returnsNullForInvalidId() = runTestWithTimeout {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val result = transactionForDao.getTransactionFor(
            id = INVALID_ID,
        )

        Assert.assertNull(result)
    }

    @Test
    fun updateTransactionForValues() = runTestWithTimeout {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        val testTitle1 = "Tim"
        val testTitle2 = "Rock"
        transactionForDao.updateTransactionForValues(
            testTransactionForValues[0].copy(
                id = TEST_ID_1,
                title = testTitle1,
            ),
            testTransactionForValues[1].copy(
                id = TEST_ID_2,
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
    fun deleteTransactionFor_deleteDataOfGivenId() = runTestWithTimeout {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        transactionForDao.deleteTransactionFor(
            id = TEST_ID_1,
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
    fun deleteTransactionFor_noDeletionForInvalidId() = runTestWithTimeout {
        transactionForDao.insertTransactionForValues(
            transactionForValues = testTransactionForValues.toTypedArray(),
        )

        transactionForDao.deleteTransactionFor(
            id = INVALID_ID,
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
        transactionForDao = myRoomDatabase.transactionForDao()
    }

    private fun tearDown() {
        myRoomDatabase.close()
    }
    // endregion

    companion object {
        private const val TEST_ID_1 = 123
        private const val TEST_ID_2 = 234
        private const val TEST_TITLE_1 = "Sam"
        private const val TEST_TITLE_2 = "Jim"
        private const val INVALID_ID = 987
        private val testTransactionForValues = listOf(
            TransactionForEntity(
                id = TEST_ID_1,
                title = TEST_TITLE_1,
            ),
            TransactionForEntity(
                id = TEST_ID_2,
                title = TEST_TITLE_2,
            ),
        )
    }
}
