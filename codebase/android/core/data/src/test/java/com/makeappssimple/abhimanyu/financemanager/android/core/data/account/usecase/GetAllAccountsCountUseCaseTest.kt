package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsCountUseCaseImpl
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class GetAllAccountsCountUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAllAccountsCountUseCase: GetAllAccountsCountUseCase

    @Before
    public fun setUp() {
        getAllAccountsCountUseCase =
            GetAllAccountsCountUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        getAllAccountsCountUseCase()

        verify(
            mock = accountRepository,
        ).getAllAccountsCount()
    }
}
