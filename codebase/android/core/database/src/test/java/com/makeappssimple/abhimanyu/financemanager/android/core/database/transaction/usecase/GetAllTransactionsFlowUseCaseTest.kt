package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllTransactionsFlowUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var getAllTransactionsFlowUseCase: GetAllTransactionsFlowUseCase

    @Before
    fun setUp() {
        getAllTransactionsFlowUseCase = GetAllTransactionsFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllTransactionsFlowUseCase()

        verify(
            mock = transactionRepository,
        ).getAllTransactionsFlow()
    }
}
