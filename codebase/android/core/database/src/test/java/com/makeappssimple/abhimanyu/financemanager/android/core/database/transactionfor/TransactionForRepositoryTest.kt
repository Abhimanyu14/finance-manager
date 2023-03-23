package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestTransactionForValues
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TransactionForRepositoryTest {
    private val transactionForDao: TransactionForDao = mock()
    private val testId: Int = 1
    private val testTransactionForValues: Array<TransactionFor> = getTestTransactionForValues()
    private lateinit var transactionForRepository: TransactionForRepository

    @Before
    fun setUp() {
        transactionForRepository = TransactionForRepositoryImpl(
            transactionForDao = transactionForDao,
        )
    }

    @Test
    fun getAllTransactionForValuesFlow() {
        transactionForRepository.getAllTransactionForValuesFlow()

        verify(
            mock = transactionForDao,
        ).getAllTransactionForValuesFlow()
    }

    @Test
    fun getTransactionFor() = runTest {
        transactionForRepository.getTransactionFor(
            id = testId,
        )

        verify(
            mock = transactionForDao,
        ).getTransactionFor(
            id = testId,
        )
    }

    @Test
    fun insertTransactionForValues() = runTest {
        transactionForRepository.insertTransactionForValues(
            *testTransactionForValues,
        )

        verify(
            mock = transactionForDao,
        ).insertTransactionForValues(
            *testTransactionForValues,
        )
    }

    @Test
    fun updateTransactionForValues() = runTest {
        transactionForRepository.updateTransactionForValues(
            *testTransactionForValues,
        )

        verify(
            mock = transactionForDao,
        ).updateTransactionForValues(
            *testTransactionForValues,
        )
    }

    @Test
    fun deleteTransactionFor() = runTest {
        transactionForRepository.deleteTransactionFor(
            id = testId,
        )

        verify(
            mock = transactionForDao,
        ).deleteTransactionFor(
            id = testId,
        )
    }
}
