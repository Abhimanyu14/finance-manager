package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestTransaction
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestTransactions
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TransactionRepositoryImplTest {
    private val transactionDao: TransactionDao = mock()
    private val id: Int = 1
    private val transaction: Transaction = getTestTransaction()
    private val transactions: Array<Transaction> = getTestTransactions()
    private lateinit var transactionRepository: TransactionRepository

    @Before
    fun setUp() {
        transactionRepository = TransactionRepositoryImpl(
            transactionDao = transactionDao,
        )
    }

    @Test
    fun getTransactions() {
        transactionRepository.transactions

        verify(
            mock = transactionDao,
        ).getTransactions()
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
    fun insertTransaction() = runTest {
        transactionRepository.insertTransaction(
            transaction = transaction,
        )

        verify(
            mock = transactionDao,
        ).insertTransaction(
            transaction = transaction,
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
        )

        verify(
            mock = transactionDao,
        ).deleteTransaction(
            id = id,
        )
    }

    @Test
    fun deleteTransactions() = runTest {
        transactionRepository.deleteTransactions(
            *transactions,
        )

        verify(
            mock = transactionDao,
        ).deleteTransactions(
            *transactions,
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
