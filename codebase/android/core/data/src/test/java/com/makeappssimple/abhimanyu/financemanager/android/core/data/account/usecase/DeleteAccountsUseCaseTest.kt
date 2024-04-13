package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class DeleteAccountsUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private lateinit var deleteAccountsUseCase: DeleteAccountsUseCase

    @Before
    public fun setUp() {
        deleteAccountsUseCase =
            DeleteAccountsUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                accountRepository = accountRepository,
            )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        val account = getTestAccounts().first()
        deleteAccountsUseCase(
            account,
        )

        verify(
            mock = accountRepository,
        ).deleteAccounts(
            account,
        )
    }
}
