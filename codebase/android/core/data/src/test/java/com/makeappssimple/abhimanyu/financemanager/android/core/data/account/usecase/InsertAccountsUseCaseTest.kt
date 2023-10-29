package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertAccountsUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private lateinit var insertAccountsUseCase: InsertAccountsUseCase

    @Before
    fun setUp() {
        insertAccountsUseCase = InsertAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val accounts = getTestAccounts()
        insertAccountsUseCase(
            accounts = accounts,
        )

        verify(
            mock = accountRepository,
        ).insertAccounts(
            accounts = accounts,
        )
    }
}
