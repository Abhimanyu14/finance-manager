package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class AccountDaoTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var accountDao: AccountDao

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
        accountDao = myRoomDatabase.accountDao()
    }

    @After
    fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    fun getAllAccounts() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAllAccounts()

        // Note: Order of returned items will not match ordered of actual list
        Assert.assertEquals(
            testAccounts.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllAccountsFlow() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAllAccountsFlow().first()

        Assert.assertEquals(
            testAccounts.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllAccountsCount() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAllAccountsCount()

        Assert.assertEquals(
            testAccounts.size,
            result,
        )
    }

    @Test
    fun getAccount_returnsDataForValidId() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAccount(
            id = testId2,
        )

        Assert.assertEquals(
            testAccounts[1],
            result,
        )
    }

    @Test
    fun getAccount_returnsNullForInvalidId() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAccount(
            id = invalidId,
        )

        Assert.assertNull(result)
    }

    @Test
    fun deleteAccount_deleteDataOfGivenId() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
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
            setOf(testAccounts[1], testAccounts[2]),
            result.toSet(),
        )
    }

    @Test
    fun deleteAccount_noDeletionForInvalidId() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
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
            testAccounts.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun updateAccounts() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val testAccountName2 = "IOB"
        accountDao.updateAccounts(
            testAccounts[1]
                .copy(
                    name = testAccountName2,
                ),
            testAccounts[2]
                .copy(
                    id = 6342,
                ),
        )
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            3,
            result.size,
        )
        Assert.assertEquals(
            testAccounts[0],
            result[0],
        )
        Assert.assertEquals(
            testAccounts[2],
            result[2],
        )
        Assert.assertEquals(
            testAccountName2,
            result[1].name,
        )
    }

    @Test
    fun deleteAccounts() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        accountDao.deleteAccounts(
            testAccounts[0]
                .copy(
                    name = "Random", // Data mismatch
                ),
            testAccounts[1].copy(
                id = invalidId,
            ),
        )
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            setOf(testAccounts[1], testAccounts[2]),
            result.toSet(),
        )
    }

    @Test
    fun deleteAllAccounts() = testScope.runTest {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        accountDao.deleteAllAccounts()
        val result = accountDao.getAllAccounts()

        Assert.assertEquals(
            0,
            result.size,
        )
    }

    companion object {
        private const val testId1 = 123
        private const val testId2 = 234
        private const val testId3 = 345
        private const val invalidId = 987
        private val testAccounts = listOf(
            AccountEntity(
                id = testId1,
                balanceAmount = AmountEntity(
                    value = testId1.toLong(),
                ),
                name = "Cash",
                type = AccountType.CASH,
            ),
            AccountEntity(
                id = testId2,
                balanceAmount = AmountEntity(
                    value = testId2.toLong(),
                ),
                name = "Axis",
                type = AccountType.BANK,
            ),
            AccountEntity(
                id = testId3,
                balanceAmount = AmountEntity(
                    value = testId3.toLong(),
                ),
                name = "Paytm",
                type = AccountType.E_WALLET,
            ),
        )
    }
}
