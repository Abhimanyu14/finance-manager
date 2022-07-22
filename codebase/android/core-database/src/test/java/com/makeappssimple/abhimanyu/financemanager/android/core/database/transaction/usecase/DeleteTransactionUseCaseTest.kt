package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTransactionUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    @Before
    fun setUp() {
        deleteTransactionUseCase = DeleteTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
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
