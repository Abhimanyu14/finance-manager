package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateAccountsUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private lateinit var updateAccountsUseCase: UpdateAccountsUseCase

    @Before
    fun setUp() {
        updateAccountsUseCase =
            UpdateAccountsUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                accountRepository = accountRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val sources = getTestAccounts()
        updateAccountsUseCase(
            *sources,
        )

        verify(
            mock = accountRepository,
        ).updateAccounts(
            *sources,
        )
    }
}
