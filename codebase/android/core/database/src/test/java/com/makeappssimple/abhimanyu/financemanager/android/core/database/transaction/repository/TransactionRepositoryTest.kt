package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestSources
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestTransactions
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
    fun getAllTransactions() {
        transactionRepository.allTransactions

        verify(
            mock = transactionDao,
        ).getAllTransactions()
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
