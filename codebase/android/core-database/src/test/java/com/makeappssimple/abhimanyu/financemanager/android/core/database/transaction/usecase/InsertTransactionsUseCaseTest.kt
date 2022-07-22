package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestTransactions
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertTransactionsUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var insertTransactionsUseCase: InsertTransactionsUseCase

    @Before
    fun setUp() {
        insertTransactionsUseCase = InsertTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val transactions = getTestTransactions()
        insertTransactionsUseCase(
            *transactions,
        )

        verify(
            mock = transactionRepository,
        ).insertTransactions(
            *transactions,
        )
    }
}
