package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTransactionUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    @Before
    fun setUp() {
        deleteTransactionUseCase = DeleteTransactionUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val id = 3
        runBlocking {
            deleteTransactionUseCase(
                id = id,
            )

            verify(
                mock = transactionRepository,
            ).deleteTransaction(
                id = id,
            )
        }
    }
}
