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
class TransactionDaoTest {
    private lateinit var database: MyRoomDatabase
    private lateinit var dao: TransactionDao

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(
                context,
                MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        dao = database.transactionDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTransactions() = runTest {
        val transactions = getTestTransactions(
            size = 10,
            frequency = THIRTY_DAYS,
        )
        dao.insertTransactions(
            transactions = transactions,
        )
        val fetchedTransactions = dao.getAllTransactionsFlow().first().toTypedArray()

        val transactionTimes = fetchedTransactions.map {
            getReadableDateAndTime(it.transactionTimestamp)
        }
        println(transactionTimes)
        Assert.assertArrayEquals(
            transactions,
            fetchedTransactions,
        )
    }

    @Test
    fun getTransactionsBetweenTimestampsFlow() = runTest {
        val transactions = getTestTransactions(
            size = 100,
            frequency = ONE_HOUR,
        )
        dao.insertTransactions(
            transactions = transactions,
        )

        val fetchedTransactions = dao.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = timeInMillis_01_JUN_2022 - ONE_DAY,
            endingTimestamp = timeInMillis_01_JUN_2022 - 1,
        ).first().toTypedArray()

        val transactionTimes = fetchedTransactions.map {
            getReadableDateAndTime(it.transactionTimestamp)
        }
        println(transactionTimes)
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
}
