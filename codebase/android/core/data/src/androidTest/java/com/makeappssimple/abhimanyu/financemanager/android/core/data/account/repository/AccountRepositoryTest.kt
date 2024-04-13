package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import javax.inject.Inject

@HiltAndroidTest
public class AccountRepositoryTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val accountDao: AccountDao = mock()
    private val id: Int = 1
    private val accounts: Array<Account> = getTestAccounts()
    private lateinit var accountRepository: AccountRepository

    @Inject
    public lateinit var dispatcherProvider: DispatcherProvider

    @Before
    public fun setUp() {
        accountRepository = AccountRepositoryImpl(
            accountDao = accountDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Test
    public fun getAllAccountsFlow() {
        accountRepository.getAllAccountsFlow()

        verify(
            mock = accountDao,
        ).getAllAccountsFlow()
    }

    @Ignore("Fix this test")
    @Test
    public fun getAllAccounts(): TestResult = runTest {
        accountRepository.getAllAccounts()

        verify(
            mock = accountDao,
        ).getAllAccounts()
    }

    @Test
    public fun getAllAccountsCount(): TestResult = runTest {
        accountRepository.getAllAccountsCount()

        verify(
            mock = accountDao,
        ).getAllAccountsCount()
    }

    @Test
    public fun getAccount(): TestResult = runTest {
        accountRepository.getAccount(
            id = id,
        )

        verify(
            mock = accountDao,
        ).getAccount(
            id = id,
        )
    }

    @Ignore("Fix this test")
    @Test
    public fun getAccounts(): TestResult = runTest {
        accountRepository.getAccounts(
            ids = listOf(id),
        )

        verify(
            mock = accountDao,
        ).getAccounts(
            ids = listOf(id),
        )
    }

    @Test
    public fun insertAccounts(): TestResult = runTest {
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
    public fun updateAccounts(): TestResult = runTest {
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
    public fun deleteAccount(): TestResult = runTest {
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
    public fun deleteAccounts(): TestResult = runTest {
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
