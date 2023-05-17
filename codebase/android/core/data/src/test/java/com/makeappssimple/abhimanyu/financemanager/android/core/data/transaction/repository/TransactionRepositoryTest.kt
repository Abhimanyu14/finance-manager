package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.TransactionDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TransactionRepositoryTest {
    private val transactionDao: TransactionDao = mock()
    private val transactionDataSource: TransactionDataSource = mock()
    private val testId: Int = 1
    private val testTransactions: Array<Transaction> = getTestTransactions()
    private lateinit var transactionRepository: TransactionRepository

    @Before
    fun setUp() {
        transactionRepository = TransactionRepositoryImpl(
            transactionDao = transactionDao,
            transactionDataSource = transactionDataSource,
        )
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
            *testTransactions.map {
                it.asEntity()
            }.toTypedArray(),
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
