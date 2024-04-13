package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCaseImpl
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class GetAccountUseCaseTest {
    private val accountRepository: AccountRepository = mock()
    private lateinit var getAccountUseCase: GetAccountUseCase

    @Before
    public fun setUp() {
        getAccountUseCase =
            GetAccountUseCaseImpl(
                accountRepository = accountRepository,
            )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        val id = 3
        getAccountUseCase(
            id = id,
        )

        verify(
            mock = accountRepository,
        ).getAccount(
            id = id,
        )
    }
}
