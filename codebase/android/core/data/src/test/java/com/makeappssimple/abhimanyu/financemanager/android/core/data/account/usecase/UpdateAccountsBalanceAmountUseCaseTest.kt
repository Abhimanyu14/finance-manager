package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class UpdateAccountsBalanceAmountUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private lateinit var updateAccountsBalanceAmountUseCase: UpdateAccountsBalanceAmountUseCase

    @Before
    fun setUp() {
        updateAccountsBalanceAmountUseCase =
            UpdateAccountsBalanceAmountUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                accountRepository = accountRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val testData = listOf(
            Pair(0, 1L),
            Pair(1, 2L),
            Pair(2, 3L),
        )
        updateAccountsBalanceAmountUseCase(
            testData,
        )

        verify(
            mock = accountRepository,
        ).updateAccountBalanceAmount(
            testData,
        )
    }
}
