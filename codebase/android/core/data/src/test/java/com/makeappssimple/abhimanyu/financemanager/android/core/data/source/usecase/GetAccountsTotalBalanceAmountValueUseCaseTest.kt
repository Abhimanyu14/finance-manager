package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAccountsTotalBalanceAmountValueUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase

    @Before
    fun setUp() {
        getAccountsTotalBalanceAmountValueUseCase =
            GetAccountsTotalBalanceAmountValueUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAccountsTotalBalanceAmountValueUseCase()

        verify(
            mock = accountRepository,
        ).getAllAccountsFlow().map {
            it.sumOf { source ->
                source.balanceAmount.value
            }
        }
    }
}
