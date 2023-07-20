package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllAccountsFlowUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase

    @Before
    fun setUp() {
        getAllAccountsFlowUseCase =
            GetAllAccountsFlowUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllAccountsFlowUseCase()

        verify(
            mock = accountRepository,
        ).getAllAccountsFlow()
    }
}
