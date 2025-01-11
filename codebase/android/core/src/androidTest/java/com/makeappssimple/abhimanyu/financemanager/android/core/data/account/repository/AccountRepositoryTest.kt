package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.getTestAccounts
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake.FakeAccountDaoImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.verify
import javax.inject.Inject

@HiltAndroidTest
internal class AccountRepositoryTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val accountDao: AccountDao = FakeAccountDaoImpl()
    private val id: Int = 1
    private val accounts: Array<Account> = getTestAccounts()
    private lateinit var accountRepository: AccountRepository

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        hiltRule.inject()

        accountRepository = AccountRepositoryImpl(
            accountDao = accountDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Test
    fun sample() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun getAllAccountsFlow() {
        accountRepository.getAllAccountsFlow()

        verify(
            mock = accountDao,
        ).getAllAccountsFlow()
    }

    @Ignore("Fix this test")
    @Test
    fun getAllAccounts() = runTest {
        accountRepository.getAllAccounts()

        verify(
            mock = accountDao,
        ).getAllAccounts()
    }

    @Test
    fun getAllAccountsCount() = runTest {
        accountRepository.getAllAccountsCount()

        verify(
            mock = accountDao,
        ).getAllAccountsCount()
    }

    @Test
    fun getAccount() = runTest {
        accountRepository.getAccount(
            id = id,
        )

        verify(
            mock = accountDao,
        ).getAccount(
            id = id,
        )
    }

    @Test
    fun getAccounts() = runTest {
        accountRepository.getAccounts(
            ids = listOf(id).toImmutableList(),
        )

        verify(
            mock = accountDao,
        ).getAccounts(
            ids = listOf(id),
        )
    }

    @Test
    fun insertAccounts() = runTest {
        accountRepository.insertAccounts(
            accounts = accounts,
        )

        verify(
            mock = accountDao,
        ).insertAccounts(
            accounts = accounts.map(
                transform = Account::asEntity,
            ).toTypedArray(),
        )
    }

    @Test
    fun updateAccounts() = runTest {
        accountRepository.updateAccounts(
            accounts = accounts,
        )

        verify(
            mock = accountDao,
        ).updateAccounts(
            accounts = accounts.map(
                transform = Account::asEntity,
            ).toTypedArray(),
        )
    }

    @Test
    fun deleteAccount() = runTest {
        accountRepository.deleteAccount(
            id = id,
        )

        verify(
            mock = accountDao,
        ).deleteAccount(
            id = id,
        )
    }

    @Test
    fun deleteAccounts() = runTest {
        accountRepository.deleteAccounts(
            accounts = accounts,
        )

        verify(
            mock = accountDao,
        ).deleteAccounts(
            accounts = accounts.map(
                transform = Account::asEntity,
            ).toTypedArray(),
        )
    }
}
