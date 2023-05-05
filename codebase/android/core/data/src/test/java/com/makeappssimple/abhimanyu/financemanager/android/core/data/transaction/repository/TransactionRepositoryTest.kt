package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestSources
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TransactionRepositoryTest {
    private val transactionDao: TransactionDao = mock()
    private val testId: Int = 1
    private val testTransactions: Array<Transaction> = getTestTransactions()
    private val testSources: Array<Source> = getTestSources()
    private lateinit var transactionRepository: TransactionRepository

    @Before
    fun setUp() {
        transactionRepository = TransactionRepositoryImpl(
            transactionDao = transactionDao,
        )
    }

    @Test
    fun getAllTransactionsFlow() {
        transactionRepository.getAllTransactionsFlow()

        verify(
            mock = transactionDao,
        ).getAllTransactionsFlow()
    }

    @Test
    fun getTransactionsCount() = runTest {
        transactionRepository.getTransactionsCount()

        verify(
            mock = transactionDao,
        ).getTransactionsCount()
    }

    @Test
    fun getTransaction() = runTest {
        transactionRepository.getTransaction(
            id = testId,
        )

        verify(
            mock = transactionDao,
        ).getTransaction(
            id = testId,
        )
    }

    @Test
    fun insertTransactions() = runTest {
        transactionRepository.insertTransactions(
            *testTransactions,
        )

        verify(
            mock = transactionDao,
        ).insertTransactions(
            *testTransactions,
        )
    }

    @Test
    fun deleteTransaction() = runTest {
        transactionRepository.deleteTransaction(
            id = testId,
            sources = testSources,
        )

        verify(
            mock = transactionDao,
        ).deleteTransaction(
            id = testId,
            sources = testSources,
        )
    }

    @Test
    fun deleteAllTransactions() = runTest {
        transactionRepository.deleteAllTransactions()

        verify(
            mock = transactionDao,
        ).deleteAllTransactions()
    }
}
