package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllAccountsCountUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAllAccountsCountUseCase: GetAllAccountsCountUseCase

    @Before
    fun setUp() {
        getAllAccountsCountUseCase =
            GetAllAccountsCountUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllAccountsCountUseCase()

        verify(
            mock = accountRepository,
        ).getAllAccountsCount()
    }
}
