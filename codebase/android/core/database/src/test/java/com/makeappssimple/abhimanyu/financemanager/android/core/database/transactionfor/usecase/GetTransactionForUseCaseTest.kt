package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetTransactionForUseCaseTest {
    private val transactionForRepository: TransactionForRepository = mock()
    private lateinit var getTransactionForUseCase: GetTransactionForUseCase

    @Before
    fun setUp() {
        getTransactionForUseCase = GetTransactionForUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
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
