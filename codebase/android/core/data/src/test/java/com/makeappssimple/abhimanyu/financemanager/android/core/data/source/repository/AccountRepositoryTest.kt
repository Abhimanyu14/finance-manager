package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestSources
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AccountRepositoryTest {
    private val accountDao: AccountDao = mock()
    private val id: Int = 1
    private val sources: Array<Source> = getTestSources()
    private lateinit var accountRepository: AccountRepository

    @Before
    fun setUp() {
        accountRepository = AccountRepositoryImpl(
            accountDao = accountDao,
        )
    }

    @Test
    fun getAllSourcesFlow() {
        accountRepository.getAllAccountsFlow()

        verify(
            mock = accountDao,
        ).getAllAccountsFlow()
    }

    @Ignore("Fix this test")
    @Test
    fun getAllSources() = runTest {
        accountRepository.getAllAccounts()

        verify(
            mock = accountDao,
        ).getAllAccounts()
    }

    @Test
    fun getAllSourcesCount() = runTest {
        accountRepository.getAllAccountsCount()

        verify(
            mock = accountDao,
        ).getAllAccountsCount()
    }

    @Test
    fun getSource() = runTest {
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
    fun getSources() = runTest {
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
    fun insertSources() = runTest {
        accountRepository.insertAccounts(
            *sources,
        )

        verify(
            mock = accountDao,
        ).insertAccounts(
            *sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @Test
    fun updateSources() = runTest {
        accountRepository.updateAccounts(
            *sources,
        )

        verify(
            mock = accountDao,
        ).updateAccounts(
            *sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @Test
    fun deleteSource() = runTest {
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
    fun deleteSources() = runTest {
        accountRepository.deleteAccounts(
            *sources,
        )

        verify(
            mock = accountDao,
        ).deleteAccounts(
            *sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }
}
