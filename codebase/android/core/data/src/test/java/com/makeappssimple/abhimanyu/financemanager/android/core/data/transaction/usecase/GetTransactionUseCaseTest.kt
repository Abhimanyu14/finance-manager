package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCaseImpl
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
