package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetTransactionUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var getTransactionUseCase: GetTransactionUseCase

    @Before
    fun setUp() {
        getTransactionUseCase = GetTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        getTransactionUseCase(
            id = id,
        )

        verify(
            mock = transactionRepository,
        ).getTransaction(
            id = id,
        )
    }
}
