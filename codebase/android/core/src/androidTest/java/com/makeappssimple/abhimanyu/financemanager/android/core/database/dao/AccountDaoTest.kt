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
internal class AccountDaoTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region dependencies
    private lateinit var myRoomDatabase: MyRoomDatabase
    // endregion

    // region SUT
    private lateinit var accountDao: AccountDao
    // endregion

    @Test
    fun getAllAccounts() = runTestWithTimeout {
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
    fun getAllAccountsFlow() = runTestWithTimeout {
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
    fun getAllAccountsCount() = runTestWithTimeout {
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
    fun getAccount_returnsDataForValidId() = runTestWithTimeout {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAccount(
            id = TEST_ID_2,
        )

        Assert.assertEquals(
            testAccounts[1],
            result,
        )
    }

    @Test
    fun getAccount_returnsNullForInvalidId() = runTestWithTimeout {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        val result = accountDao.getAccount(
            id = INVALID_ID,
        )

        Assert.assertNull(result)
    }

    @Test
    fun deleteAccount_deleteDataOfGivenId() = runTestWithTimeout {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        accountDao.deleteAccount(
            id = TEST_ID_1,
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
    fun deleteAccount_noDeletionForInvalidId() = runTestWithTimeout {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        accountDao.deleteAccount(
            id = INVALID_ID,
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
    fun updateAccounts() = runTestWithTimeout {
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
    fun deleteAccounts() = runTestWithTimeout {
        accountDao.insertAccounts(
            accounts = testAccounts.toTypedArray(),
        )

        accountDao.deleteAccounts(
            testAccounts[0]
                .copy(
                    name = "Random", // Data mismatch
                ),
            testAccounts[1].copy(
                id = INVALID_ID,
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
    fun deleteAllAccounts() = runTestWithTimeout {
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
        accountDao = myRoomDatabase.accountDao()
    }

    private fun tearDown() {
        myRoomDatabase.close()
    }
    // endregion

    companion object {
        private const val TEST_ID_1 = 123
        private const val TEST_ID_2 = 234
        private const val TEST_ID_3 = 345
        private const val INVALID_ID = 987
        private val testAccounts = listOf(
            AccountEntity(
                id = TEST_ID_1,
                balanceAmount = AmountEntity(
                    value = TEST_ID_1.toLong(),
                ),
                name = "Cash",
                type = AccountType.CASH,
            ),
            AccountEntity(
                id = TEST_ID_2,
                balanceAmount = AmountEntity(
                    value = TEST_ID_2.toLong(),
                ),
                name = "Axis",
                type = AccountType.BANK,
            ),
            AccountEntity(
                id = TEST_ID_3,
                balanceAmount = AmountEntity(
                    value = TEST_ID_3.toLong(),
                ),
                name = "Paytm",
                type = AccountType.E_WALLET,
            ),
        )
    }
}
