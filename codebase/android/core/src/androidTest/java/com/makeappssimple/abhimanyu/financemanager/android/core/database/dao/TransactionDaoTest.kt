package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.ONE_DAY
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.ONE_HOUR
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.THIRTY_DAYS
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getReadableDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.timeInMillis_01_JUN_2022
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
internal class TransactionDaoTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region dependencies
    private lateinit var myRoomDatabase: MyRoomDatabase
    // endregion

    // region SUT
    private lateinit var transactionDao: TransactionDao
    // endregion

    @Test
    fun insertTransactions() = runTestWithTimeout {
        val transactions = getTestTransactions(
            size = 10,
            frequency = THIRTY_DAYS,
        )
        transactionDao.insertTransactions(
            transactions = transactions,
        )
        val fetchedTransactions = transactionDao.getAllTransactionsFlow().first().toTypedArray()

        val transactionTimes = fetchedTransactions.map {
            getReadableDateAndTime(it.transactionTimestamp)
        }
        Assert.assertArrayEquals(
            transactions,
            fetchedTransactions,
        )
    }

    @Test
    fun getTransactionsBetweenTimestampsFlow() = runTestWithTimeout {
        val transactions = getTestTransactions(
            size = 100,
            frequency = ONE_HOUR,
        )
        transactionDao.insertTransactions(
            transactions = transactions,
        )

        val fetchedTransactions = transactionDao.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = timeInMillis_01_JUN_2022 - ONE_DAY,
            endingTimestamp = timeInMillis_01_JUN_2022 - 1,
        ).first().toTypedArray()

        val transactionTimes = fetchedTransactions.map {
            getReadableDateAndTime(it.transactionTimestamp)
        }
        Assert.assertEquals(
            24,
            fetchedTransactions.size,
        )
        val result = transactions.toMutableList().filterIndexed { index, _ ->
            index in 1..24
        }
        Assert.assertArrayEquals(
            result.toTypedArray(),
            fetchedTransactions,
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
        transactionDao = myRoomDatabase.transactionDao()
    }

    private fun tearDown() {
        myRoomDatabase.close()
    }
    // endregion
}
