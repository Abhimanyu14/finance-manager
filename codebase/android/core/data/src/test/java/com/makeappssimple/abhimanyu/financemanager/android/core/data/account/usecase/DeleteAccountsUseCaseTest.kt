package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccounts
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteAccountsUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private lateinit var deleteAccountsUseCase: DeleteAccountsUseCase

    @Before
    fun setUp() {
        deleteAccountsUseCase =
            DeleteAccountsUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                accountRepository = accountRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
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
