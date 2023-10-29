package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AccountRepositoryTest {
    private val accountDao: AccountDao = mock()
    private val id: Int = 1
    private val accounts: Array<Account> = getTestAccounts()
    private lateinit var accountRepository: AccountRepository

    @Before
    fun setUp() {
        accountRepository = AccountRepositoryImpl(
            accountDao = accountDao,
        )
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

    @Ignore("Fix this test")
    @Test
    fun getAccounts() = runTest {
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
    fun insertAccounts() = runTest {
        accountRepository.insertAccounts(
            *accounts,
        )

        verify(
            mock = accountDao,
        ).insertAccounts(
            *accounts.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @Test
    fun updateAccounts() = runTest {
        accountRepository.updateAccounts(
            *accounts,
        )

        verify(
            mock = accountDao,
        ).updateAccounts(
            *accounts.map {
                it.asEntity()
            }.toTypedArray(),
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
            *accounts,
        )

        verify(
            mock = accountDao,
        ).deleteAccounts(
            *accounts.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }
}
