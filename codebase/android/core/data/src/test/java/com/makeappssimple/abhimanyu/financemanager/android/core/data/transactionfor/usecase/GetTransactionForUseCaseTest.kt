package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCaseImpl
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class GetTransactionForUseCaseTest {
    private val transactionForRepository: TransactionForRepository = mock()
    private lateinit var getTransactionForUseCase: GetTransactionForUseCase

    @Before
    public fun setUp() {
        getTransactionForUseCase = GetTransactionForUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        val id = 3
        getTransactionForUseCase(
            id = id,
        )

        verify(
            mock = transactionForRepository,
        ).getTransactionFor(
            id = id,
        )
    }
}
