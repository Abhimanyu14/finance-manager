package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
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
class AccountDaoTest {
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var accountDao: AccountDao

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        accountDao = myRoomDatabase.sourceDao()
    }

    @After
    fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    fun getAllSources() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        val result = accountDao.getAllAccounts()

        // Note: Order of returned items will not match ordered of actual list
        Assert.assertEquals(
            testSources.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllSourcesFlow() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        val result = accountDao.getAllAccountsFlow().first()

        Assert.assertEquals(
            testSources.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllSourcesCount() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        val result = accountDao.getAllAccountsCount()

        Assert.assertEquals(
            testSources.size,
            result,
        )
    }

    @Test
    fun getSource_returnsDataForValidId() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        val result = accountDao.getAccount(
            id = testId2,
        )

        Assert.assertEquals(
            testSources[1],
            result,
        )
    }

    @Test
    fun getSource_returnsNullForInvalidId() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        val result = accountDao.getAccount(
            id = invalidId,
        )

        Assert.assertNull(result)
    }

    @Test
    fun deleteSource_deleteDataOfGivenId() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        accountDao.deleteAccount(
            id = testId1,
        )
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            setOf(testSources[1], testSources[2]),
            result.toSet(),
        )
    }

    @Test
    fun deleteSource_noDeletionForInvalidId() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        accountDao.deleteAccount(
            id = invalidId,
        )
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            3,
            result.size,
        )
        Assert.assertEquals(
            testSources.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun updateSources() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        val testSourceName2 = "IOB"
        accountDao.updateAccounts(
            testSources[1].copy(
                name = testSourceName2,
            ),
            testSources[2].copy(
                id = 6342,
            ),
        )
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            3,
            result.size,
        )
        Assert.assertEquals(
            testSources[0],
            result[0],
        )
        Assert.assertEquals(
            testSources[2],
            result[2],
        )
        Assert.assertEquals(
            testSourceName2,
            result[1].name,
        )
    }

    @Test
    fun deleteSources() = runTest {
        accountDao.insertAccounts(
            sources = testSources.toTypedArray(),
        )

        accountDao.deleteAccounts(
            testSources[0].copy(
                name = "Random", // Data mismatch
            ),
            testSources[1].copy(
                id = invalidId,
            ),
        )
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            setOf(testSources[1], testSources[2]),
            result.toSet(),
        )
    }

    companion object {
        private const val testId1 = 123
        private const val testId2 = 234
        private const val testId3 = 345
        private const val invalidId = 987
        private val testSources = listOf(
            SourceEntity(
                id = testId1,
                balanceAmount = AmountEntity(
                    value = testId1.toLong(),
                ),
                name = "Cash",
                type = SourceType.CASH,
            ),
            SourceEntity(
                id = testId2,
                balanceAmount = AmountEntity(
                    value = testId2.toLong(),
                ),
                name = "Axis",
                type = SourceType.BANK,
            ),
            SourceEntity(
                id = testId3,
                balanceAmount = AmountEntity(
                    value = testId3.toLong(),
                ),
                name = "Paytm",
                type = SourceType.E_WALLET,
            ),
        )
    }
}
