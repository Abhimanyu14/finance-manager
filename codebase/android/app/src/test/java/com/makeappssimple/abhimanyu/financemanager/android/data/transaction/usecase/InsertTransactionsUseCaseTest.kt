package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestTransactions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertTransactionsUseCaseTest {
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var insertTransactionsUseCase: InsertTransactionsUseCase

    @Before
    fun setUp() {
        insertTransactionsUseCase = InsertTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val transactions = getTestTransactions()
        runBlocking {
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
}
