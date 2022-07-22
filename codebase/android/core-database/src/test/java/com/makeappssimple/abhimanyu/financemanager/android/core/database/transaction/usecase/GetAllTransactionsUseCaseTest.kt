package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllTransactionsUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var getAllTransactionsUseCase: GetAllTransactionsUseCase

    @Before
    fun setUp() {
        getAllTransactionsUseCase = GetAllTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllTransactionsUseCase()

        verify(
            mock = transactionRepository,
        ).allTransactions
    }
}
