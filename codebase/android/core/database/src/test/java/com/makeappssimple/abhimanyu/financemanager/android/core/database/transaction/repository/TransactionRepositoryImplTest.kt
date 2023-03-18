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

class TransactionRepositoryImplTest {
    private val transactionDao: TransactionDao = mock()
    private val id: Int = 1
    private val transactions: Array<Transaction> = getTestTransactions()
    private val sources: Array<Source> = getTestSources()
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
            id = id,
        )

        verify(
            mock = transactionDao,
        ).getTransaction(
            id = id,
        )
    }

    @Test
    fun insertTransactions() = runTest {
        transactionRepository.insertTransactions(
            *transactions,
        )

        verify(
            mock = transactionDao,
        ).insertTransactions(
            *transactions,
        )
    }

    @Test
    fun deleteTransaction() = runTest {
        transactionRepository.deleteTransaction(
            id = id,
            sources = sources,
        )

        verify(
            mock = transactionDao,
        ).deleteTransaction(
            id = id,
            sources = sources,
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
