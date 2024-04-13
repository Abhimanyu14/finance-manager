package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class UpdateAccountsUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private lateinit var updateAccountsUseCase: UpdateAccountsUseCase

    @Before
    public fun setUp() {
        updateAccountsUseCase =
            UpdateAccountsUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                accountRepository = accountRepository,
            )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        val accounts = getTestAccounts()
        updateAccountsUseCase(
            accounts = accounts,
        )

        verify(
            mock = accountRepository,
        ).updateAccounts(
            accounts = accounts,
        )
    }
}
