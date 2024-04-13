package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCaseImpl
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class GetAllAccountsFlowUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase

    @Before
    public fun setUp() {
        getAllAccountsFlowUseCase =
            GetAllAccountsFlowUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        getAllAccountsFlowUseCase()

        verify(
            mock = accountRepository,
        ).getAllAccountsFlow()
    }
}
