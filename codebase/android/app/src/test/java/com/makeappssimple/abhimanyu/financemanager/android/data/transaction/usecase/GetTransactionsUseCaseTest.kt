package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetTransactionsUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Before
    fun setUp() {
        getTransactionsUseCase = GetTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        runBlocking {
            getTransactionsUseCase()

            verify(
                mock = transactionRepository,
            ).transactions
        }
    }
}
