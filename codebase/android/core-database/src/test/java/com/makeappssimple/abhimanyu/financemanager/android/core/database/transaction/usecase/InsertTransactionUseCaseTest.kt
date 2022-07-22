package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestTransaction
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertTransactionUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var insertTransactionUseCase: InsertTransactionUseCase

    @Before
    fun setUp() {
        insertTransactionUseCase = InsertTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val transaction = getTestTransaction()
        insertTransactionUseCase(
            transaction = transaction,
        )

        verify(
            mock = transactionRepository,
        ).insertTransaction(
            transaction = transaction,
        )
    }
}
