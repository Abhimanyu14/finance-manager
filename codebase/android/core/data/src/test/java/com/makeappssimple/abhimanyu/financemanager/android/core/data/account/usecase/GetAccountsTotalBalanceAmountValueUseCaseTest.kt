package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCaseImpl
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class GetAccountsTotalBalanceAmountValueUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase

    @Before
    public fun setUp() {
        getAccountsTotalBalanceAmountValueUseCase =
            GetAccountsTotalBalanceAmountValueUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        getAccountsTotalBalanceAmountValueUseCase()

        verify(
            mock = accountRepository,
        ).getAllAccountsFlow().map {
            it.sumOf { account ->
                account.balanceAmount.value
            }
        }
    }
}
